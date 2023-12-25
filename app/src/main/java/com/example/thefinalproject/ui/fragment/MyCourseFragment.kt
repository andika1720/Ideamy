package com.example.thefinalproject.ui.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thefinalproject.R
import com.example.thefinalproject.adapter.AdapterMyCourseNew
import com.example.thefinalproject.databinding.FragmentMyCourseBinding
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.network.model.course.DataCategory
import com.example.thefinalproject.network.model.course.ListResponse
import com.example.thefinalproject.ui.fragment.botsheet.BotSheetLogin
import com.example.thefinalproject.ui.fragment.botsheet.BotsheetSelangkah
import com.example.thefinalproject.util.SharePref
import com.example.thefinalproject.util.Status

import com.google.android.material.tabs.TabLayout

import org.koin.android.ext.android.inject

@Suppress("SameParameterValue")
class MyCourseFragment : Fragment(), AdapterMyCourseNew.CourseClick {
    private lateinit var sharePref: SharePref
    private var _binding: FragmentMyCourseBinding? = null
    private val binding get() = _binding!!
    private val viewMode : ViewModelAll by inject()
    private var categorys: List<DataCategory> = emptyList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragmen
        _binding = FragmentMyCourseBinding.inflate(layoutInflater,container,false)
        sharePref = SharePref

        tabLayout()


        binding.etSearch.setOnFocusChangeListener {_,focus ->
            if (focus){
                findNavController().navigate(R.id.searchFragment)
            }
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchList(null,null,null,null,null)
        binding.tabLayoutKursus.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        fetchList(null,null,null,null,null)
                    }
                    1 -> {
                        fetchList(null,null,null,"premium",null)
                    }
                    else -> {
                        fetchList(null,null,null,"free",null)
                    }
                }
            }


            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

    private fun showListHorizontal(data: ListResponse?) {
        val adapter = AdapterMyCourseNew(this)
        adapter.sendList(data?.data ?: emptyList())
        binding.rvCourse.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.rvCourse.adapter = adapter
    }



    private fun tabLayout(){
        val allTab = binding.tabLayoutKursus.newTab()
        allTab.text = "SemuaKelas"
        binding.tabLayoutKursus.addTab(allTab)

        val premiumTab = binding.tabLayoutKursus.newTab()
        premiumTab.text = "Premium"
        binding.tabLayoutKursus.addTab(premiumTab)

        val freeTab = binding.tabLayoutKursus.newTab()
        freeTab.text = "Free"
        binding.tabLayoutKursus.addTab(freeTab)
    }

    private fun fetchList(id: String?,level: String?,category: String?, type: String?, search: String?) {
        viewMode.getFilterCourse(id, level,category, type, search).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    categorys = it.data?.data ?: emptyList()
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

    override fun onCourseItemClick(data: DataCategory) {
        val bundle = Bundle().apply {
            putString("selectedId", data.id)
        }
        val isLogin = SharePref.getPref(SharePref.Enum.PREF_NAME.value)
        if (isLogin != null) {
                val bottomSheetSelangkah = BotsheetSelangkah()
                bottomSheetSelangkah.setCourseId(bundle.getString("selectedId") ?: "")
                bottomSheetSelangkah.show(childFragmentManager, bottomSheetSelangkah.tag)

        } else {
            val botsheetLogin = BotSheetLogin()
            botsheetLogin.show(childFragmentManager, botsheetLogin.tag)
        }
    }

}


