package com.example.thefinalproject.ui.fragment

import com.example.thefinalproject.util.SharePref
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
import com.example.thefinalproject.R
import com.example.thefinalproject.adapter.AdapterCategory
import com.example.thefinalproject.adapter.AdapterKursusPopuler2

import com.example.thefinalproject.databinding.FragmentHomeBinding
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.network.model.course.CategoryResponse
import com.example.thefinalproject.network.model.course.DataCategory
import com.example.thefinalproject.network.model.course.ListResponse
import com.example.thefinalproject.ui.fragment.botsheet.BotSheetLogin
import com.example.thefinalproject.ui.fragment.botsheet.BotsheetSelangkah


import com.example.thefinalproject.util.Status
import com.google.android.material.tabs.TabLayout
import org.koin.android.ext.android.inject

class HomeFragment : Fragment(),AdapterKursusPopuler2.CourseClick {

    private lateinit var binding: FragmentHomeBinding
    private val viewMode : ViewModelAll by inject()
    private var categorys: List<DataCategory> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)


        fetchCategory(null)


        binding.tabLayoutKursus.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> fetchList(null, null, null, null,null)
                    1 -> fetchList(null, null, "Web Development", null,null)
                    2 -> fetchList(null, null, "Android Development", null,null)
                    3 -> fetchList(null, null, "Data Science", null,null)
                    4 -> fetchList(null, null, "UI/UX Design", null,null)
                    5 -> fetchList(null, null, "Product Management", null,null)
                    6 -> fetchList(null, null, "iOS Development", null,null)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }


            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
        val token = SharePref.getPref(SharePref.Enum.PREF_NAME.value)
        if (token != null) {

            Log.d("TOKEN_CHECK", "Token is not null: $token")

        } else {

            Log.d("TOKEN_CHECK", "Token is null")


        }
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
        val adapter = AdapterKursusPopuler2(this)

        val uniqueCategories = data?.data?.distinctBy { it.category }
        adapter.sendList(uniqueCategories ?: emptyList())
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
            
        
        val tabs = binding.tabLayoutKursus.newTab()
        tabs.text = "Semua Kelas"
        binding.tabLayoutKursus.addTab(tabs)
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

    override fun onCourseItemClick(data: DataCategory) {

            val bundle = Bundle().apply {
                putString("selectedId", data.id)
            }
            if (data.type == "premium") {
                val isLogin = SharePref.getPref(SharePref.Enum.PREF_NAME.value)
                if (isLogin != null) {
                    val bottomSheetFragment = BotsheetSelangkah()
                    bottomSheetFragment.setCourseId(requireContext())
                    bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
                } else {
                    val bottomSheetFragmentMustLogin = BotSheetLogin()
                    bottomSheetFragmentMustLogin.show(childFragmentManager, bottomSheetFragmentMustLogin.tag)

                }


                // Navigasi ke halaman pembayaran untuk tipe premium

            } else {
                // Navigasi ke halaman detail untuk tipe free
                findNavController().navigate(R.id.action_homeFragment2_to_detailCourse, bundle)
            }


    }

}