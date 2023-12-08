package com.example.thefinalproject.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.thefinalproject.R
import com.example.thefinalproject.adapter.AdapterCategory
import com.example.thefinalproject.adapter.AdapterCoursePage
import com.example.thefinalproject.adapter.CourseAdapter
import com.example.thefinalproject.adapter.foritemhomepage.AdapterCourse
import com.example.thefinalproject.adapter.foritemhomepage.AdapterKursusPopuler2
import com.example.thefinalproject.databinding.FragmentMyCourseBinding
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.network.model.CategoryResponse
import com.example.thefinalproject.network.model.DataCategory
import com.example.thefinalproject.network.model.DataCourse
import com.example.thefinalproject.network.model.DetailResponse
import com.example.thefinalproject.network.model.ListResponse
import com.example.thefinalproject.util.Status
import com.example.thefinalproject.util.Utils
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.ext.android.inject

class MyCourseFragment : Fragment() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private var _binding: FragmentMyCourseBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AdapterCoursePage
    private val viewMode : ViewModelAll by inject()
    private var categorys: List<DataCategory> = emptyList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragmen
        _binding = FragmentMyCourseBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchList(null,null,null,null,null)
    }

    private fun fetchList(id: String?,level: String?,category: String?, type: String?, search: String?) {
        viewMode.getFilterCourse(id, level,category, type, search).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    showTabLayouts(it.data)
                    showListHorizontal(it.data)


                    binding.progressbarCourse.isVisible = false

                }

                Status.ERROR -> {
                    binding.progressbarCourse.isVisible = false
                    Log.e("Errorr", it.message.toString())
                }

                Status.LOADING -> {
                    binding.progressbarCourse.isVisible = true
                }
            }


        }
    }
    private fun showBottomSheetDialog() {
        val bsDialog = BottomSheetDialog(requireContext())
        val bottomSheetView = layoutInflater.inflate(R.layout.botsheet_filtering_class, null)


        bsDialog.setContentView(bottomSheetView)
        bsDialog.show()



    }
    private fun showListHorizontal(data: ListResponse?) {
        categorys = data?.data ?: emptyList()
        val adapter = AdapterKursusPopuler2(onButtonClick = {

        })

        val uniqueType = data?.data?.distinctBy { it.type }
        adapter.sendList(data?.data ?: emptyList())

        binding.rvCourse.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCourse.adapter = adapter


    }

    private fun showTabLayouts(data: ListResponse?){

        val  tabLayout = binding.tabLayoutKursus
        tabLayout.removeAllTabs()

        val Tabs = tabLayout.newTab()
        Tabs.text = "Semua Kelas"
        tabLayout.addTab(Tabs)

        val uniqueCategories = data?.data?.distinctBy { it.type }
        uniqueCategories?.forEach{type ->
            val tab = tabLayout.newTab()
            tab.text = type.type
            tabLayout.addTab(tab)
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> fetchList(null, null, null, null, null)
                    else -> {
                        // Logika untuk tab lainnya (position != 0 dan position != 2)
                        val selectTabCategory = categorys[tab.position - 1].type
                        Log.e("Tabs", tab.text.toString())
                        fetchList(null, null, null, selectTabCategory, null)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

    }
}