package com.example.thefinalproject.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.thefinalproject.R
import com.example.thefinalproject.adapter.AdapterCategory
import com.example.thefinalproject.adapter.foritemhomepage.AdapterHomePage
import com.example.thefinalproject.adapter.foritemhomepage.AdapterKursusPopuler2
import com.example.thefinalproject.databinding.FragmentHomeBinding
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.network.model.course.CategoryResponse
import com.example.thefinalproject.network.model.course.DataCategory
import com.example.thefinalproject.network.model.course.ListResponse
import com.example.thefinalproject.util.Status
import com.google.android.material.tabs.TabLayout
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewMode : ViewModelAll by inject()
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapt: AdapterHomePage
    private var categorys: List<DataCategory> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)


        fetchCategory(null)

        /*
        tabLayout = binding.tabLayoutKursus
        viewPager2 = binding.viewpageKursus
        adapt= AdapterHomePage(this)
        viewPager2.setOnTouchListener { _, _ -> true  }
        viewPager2.adapter = adapt
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = "Semua Kelas"
                1 -> tab.text = "Product Management"
                2 -> tab.text = "Android Development"
                3 -> tab.text = "Data Science"
            }
        }.attach()

         */

        binding.tabLayoutKursus.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> fetchList(null, null, null, null,null)
                    else -> {
                        // Logika untuk tab lainnya (position != 0 dan position != 2)
                        val selectTabCategory = categorys[tab.position - 1].category
                        fetchList(null, null, selectTabCategory, null,null)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }


            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
        binding.lihatSemuaKursus.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment2_to_myCourseFragment2)
        }
        return binding.root

    }

    private fun fetchCategory(category: String?) {
        viewMode.getAllCategory(category).observe(viewLifecycleOwner){
            when (it.status){
                Status.SUCCESS -> {
                    showCategory(it.data)
                    showTabLayouts(it.data)
                    binding.progressbarCategory.isVisible = false

                }
                Status.ERROR -> {
                    binding.progressbarCategory.isVisible = false
                    Log.e("error", it.message.toString())
                }
                Status.LOADING -> {
                    binding.progressbarCategory.isVisible = true
                }
            }
        }
    }

    private fun fetchList(id: String?,level: String?,category: String?, type: String?, search: String?) {
        viewMode.getFilterCourse(id, level,category, type, search).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    showListHorizontal(it.data)
                    binding.progressbarKursusPopuler.isVisible = false

                }

                Status.ERROR -> {
                    binding.progressbarKursusPopuler.isVisible = false
                    Log.e("error", it.message.toString())
                }

                Status.LOADING -> {
                    binding.progressbarKursusPopuler.isVisible = true
                }
            }


        }
    }

    private fun showListHorizontal(data: ListResponse?) {
        val adapter = AdapterKursusPopuler2(onButtonClick = {courseId ->
            val bundle = Bundle().apply {
                putString("selectedId", courseId)
            }
            findNavController().navigate(R.id.action_homeFragment2_to_detailCourse,bundle)
        } )

        adapter.sendList(data?.data ?: emptyList())
        binding.rvKursuspopuler.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvKursuspopuler.adapter = adapter
    }
    private fun showCategory(data: CategoryResponse?){
        categorys = data?.data ?: emptyList()
        val adapter = AdapterCategory(object : AdapterCategory.OnClickListener{
            override fun itemClick(data: DataCategory) {
                navigatoToCourse(data)
            }
        })
            
        
        val Tabs = binding.tabLayoutKursus.newTab()
        Tabs.text = "Semua Kelas"
        binding.tabLayoutKursus.addTab(Tabs)
        val uniqueCategories = data?.data?.distinctBy { it.category }

        adapter.sendCategory(uniqueCategories ?: emptyList())
        binding.recycleviewCategory.layoutManager= GridLayoutManager(requireActivity(), 2)
        binding.recycleviewCategory.adapter = adapter
    }

    private fun showTabLayouts(data: CategoryResponse?){
        val  tabLayout = binding.tabLayoutKursus
        val uniqueCategories = data?.data?.distinctBy { it.category }
        uniqueCategories?.forEach{category ->
            val tab = tabLayout.newTab()
            tab.text = category.category
            tabLayout.addTab(tab)
        }


    }

    private fun navigatoToCourse(data: DataCategory){

        val bundle = bundleOf("key" to data)
        findNavController().navigate(R.id.action_homeFragment2_to_myCourseFragment2,bundle)
    }

}