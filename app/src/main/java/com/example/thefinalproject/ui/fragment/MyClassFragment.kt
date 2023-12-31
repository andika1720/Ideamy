package com.example.thefinalproject.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thefinalproject.R
import com.example.thefinalproject.adapter.AdapterCategory
import com.example.thefinalproject.adapter.adapterSearch.MyClassAdapter
import com.example.thefinalproject.databinding.FragmentMyClassBinding
import com.example.thefinalproject.mvvm.viewmmodel.AuthViewModel
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.network.model.course.CategoryResponse
import com.example.thefinalproject.network.model.mycourse.Course
import com.example.thefinalproject.network.model.mycourse.DataMyCourse
import com.example.thefinalproject.network.model.mycourse.MyCourseResponse
import com.example.thefinalproject.util.SharePref
import com.example.thefinalproject.util.Status
import com.google.android.material.tabs.TabLayout
import org.koin.android.ext.android.inject

@Suppress("SameParameterValue")
class MyClassFragment : Fragment() {
    private var _binding: FragmentMyClassBinding? = null
    private val binding get() = _binding!!
    private val viewMode: ViewModelAll by inject()
    private lateinit var sharePref: SharePref
    private val authViewModel: AuthViewModel by inject()
    private var categorys: List<DataMyCourse> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyClassBinding.inflate(layoutInflater, container, false)
        sharePref = SharePref
        tabLayout()
        binding.etSearch.setOnFocusChangeListener {_,focus ->
            if (focus){
                findNavController().navigate(R.id.searchFragment)
            }
        }
        fetchCategory(null)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val savedToken = sharePref.getPref(SharePref.Enum.PREF_NAME.value)

        fetchMyCourse(savedToken)
        binding.tabLayoutClass.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        fetchMyCourse(savedToken)
                    }
                    1 -> {
                        fetchMyCourse(savedToken)
                    }
                    2 -> {
                        fetchMyCourse(savedToken)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

    private fun fetchCategory(category: String?) {
        viewMode.getAllCategory(category).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    showCategory(it.data)

                    binding.progressbarCategory.isVisible = false

                }

                Status.ERROR -> {
                    binding.progressbarCategory.isVisible = false
                    Log.e("ERROR", it.message.toString())
                }

                Status.LOADING -> {
                    binding.progressbarCategory.isVisible = true
                }
            }
        }
    }

    private fun showCategory(data: CategoryResponse?) {
        val uniqueCategories = data?.data?.distinctBy { it.category }
        val adapter = AdapterCategory(null)
        adapter.sendCategory(uniqueCategories ?: emptyList())
        binding.rvCategory.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.rvCategory.adapter = adapter
    }

    private fun tabLayout(){
        val allTab = binding.tabLayoutClass.newTab()
        allTab.text = "SemuaKelas"
        binding.tabLayoutClass.addTab(allTab)

        val premiumTab = binding.tabLayoutClass.newTab()
        premiumTab.text = "In Progress"
        binding.tabLayoutClass.addTab(premiumTab)

        val freeTab = binding.tabLayoutClass.newTab()
        freeTab.text = "Selesai"
        binding.tabLayoutClass.addTab(freeTab)
    }


    private fun fetchMyCourse(token: String?) {
        authViewModel.myCourse(token).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    showListCourse(it.data)
                    Log.d("fetchCoursesucces", it.message.toString())
                    binding.progressbarCategory.isVisible = false

                }

                Status.ERROR -> {
                    binding.progressbarCategory.isVisible = false
                    Log.e("fetcherror", it.message.toString())
                }

                Status.LOADING -> {
                    binding.progressbarCategory.isVisible = true
                    Log.d("fetchload", it.message.toString())
                }
            }
        }
    }



    private fun showListCourse(data: MyCourseResponse?) {
        val adapter = MyClassAdapter { clickedCourse ->
            navigatoToCourse(clickedCourse)
        }

        data?.data?.let { dataMyCourse ->
            val courses = dataMyCourse?.courses ?: emptyList()
            if (courses.isEmpty()) {
                // Jika data kosong, atur visibility kelasKosong menjadi VISIBLE
                binding.kelasKosong.visibility = View.VISIBLE
            } else {
                // Jika data tidak kosong, atur visibility kelasKosong menjadi GONE
                binding.kelasKosong.visibility = View.GONE
            }
            adapter.submitList(courses)
        }

        binding.rvClassBerjalan.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvClassBerjalan.adapter = adapter
    }


    private fun navigatoToCourse(data: Course){
        val bundle = Bundle()
        bundle.putString("selectedId", data?.id)
        findNavController().navigate(R.id.detailCourse,bundle)
    }
}