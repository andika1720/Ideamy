package com.example.thefinalproject.ui.fragment

import android.annotation.SuppressLint
import com.example.thefinalproject.util.SharePref
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.thefinalproject.adapter.AdapterKursusPopuler2
import com.example.thefinalproject.adapter.adapterhome.AdapterCategoryHome

import com.example.thefinalproject.databinding.FragmentHomeBinding
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.network.model.course.CategoryResponse
import com.example.thefinalproject.network.model.course.DataCategory
import com.example.thefinalproject.network.model.course.ListResponse
import com.example.thefinalproject.ui.fragment.botsheet.BotSheetLogin
import com.example.thefinalproject.ui.fragment.botsheet.BotsheetSelangkah
import com.example.thefinalproject.ui.fragment.botsheet.BottomSheetDetailCategory


import com.example.thefinalproject.util.Status
import com.google.android.material.tabs.TabLayout
import org.koin.android.ext.android.inject

@Suppress("SameParameterValue")
class HomeFragment : Fragment(), AdapterKursusPopuler2.CourseClick {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewMode : ViewModelAll by inject()
    private var categorys: List<DataCategory> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater,container,false)

        binding.lihatSemuaCategory.setOnClickListener {
            val botsheetCategory = BottomSheetDetailCategory()
            botsheetCategory.show(childFragmentManager, botsheetCategory.tag)
        }

        featureSearch()
        Log.d("Fitur", "Data = ${featureSearch()}")

//        binding.etSearch.setOnFocusChangeListener {_,focus ->
//            if (focus){
//                findNavController().navigate(R.id.searchFragment)
//            }
//        }
        fetchCategory(null)



        binding.tabLayoutKursus.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            @SuppressLint("SuspiciousIndentation")
            override fun onTabSelected(tab: TabLayout.Tab) {
                val savedToken = SharePref.getPref(SharePref.Enum.PREF_NAME.value)


                    when (tab.position) {
                        0 -> fetchList(savedToken, null, null, null, null, null)
                        1 -> fetchList(savedToken, null, null, "Web Development", null, null)
                        2 -> fetchList(savedToken, null, null, "Data Science", null, null)
                        3 -> fetchList(savedToken, null, null, "UI/UX Design", null, null)
                        4 -> fetchList(savedToken, null, null, "Product Management" , null, null)
                        5 -> fetchList(savedToken, null, null, "Android Development" , null, null)
                        6 -> fetchList(savedToken, null, null, "IOS Development", null, null)
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
            findNavController().navigate(R.id.myCourseFragment2)

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

    private fun fetchList(token: String?,rating: Double?,level: String?,category: String?, type: String?, search: String?) {
        viewMode.getFilterCourse(token,rating, level,category, type, search).observe(viewLifecycleOwner) {
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

        adapter.sendList(data?.data ?: emptyList())
        binding.rvKursuspopuler.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvKursuspopuler.adapter = adapter
    }
    private fun showCategory(data: CategoryResponse?){
        categorys = data?.data ?: emptyList()
        val adapter = AdapterCategoryHome(object : AdapterCategoryHome.OnClickListener{
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
        val isLogin = SharePref.getPref(SharePref.Enum.PREF_NAME.value)
        if (isLogin != null) {
            if (data.statusPayment) {
                // Jika statusPayment true, pindah ke navigation detailCourse
                findNavController().navigate(R.id.action_homeFragment2_to_detailCourse, bundle)
                Log.d("CekStatus", "STATUS= $data")
            } else {
                // Jika statusPayment false, tampilkan bottom sheet
                val bottomSheetSelangkah = BotsheetSelangkah()
                bottomSheetSelangkah.setCourseId(bundle.getString("selectedId") ?: "")
                bottomSheetSelangkah.show(childFragmentManager, bottomSheetSelangkah.tag)
            }
        } else {
            val botsheetLogin = BotSheetLogin()
            botsheetLogin.show(childFragmentManager, botsheetLogin.tag)
        }


    }


    private fun featureSearch(){
        val searchEt = binding.etSearch
        val savedToken = SharePref.getPref(SharePref.Enum.PREF_NAME.value)
        searchEt.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    binding.rvKursuspopuler.visibility = View.VISIBLE
                    binding.recycleviewCategory.visibility = View.VISIBLE
                    binding.tvKategorys.visibility = View.VISIBLE
                    binding.lihatSemuaKursus.visibility = View.VISIBLE
                    binding.lihatSemuaCategory.visibility = View.VISIBLE
                    binding.tabLayoutKursus.visibility = View.VISIBLE
                    binding.tvKursus.visibility = View.VISIBLE
                    binding.rvSearch.visibility = View.GONE
                    binding.notFounds.visibility = View.GONE
                } else if (s.length >= 3) {
                    binding.rvKursuspopuler.visibility = View.GONE
                    binding.recycleviewCategory.visibility = View.GONE
                    binding.tvKategorys.visibility = View.GONE
                    binding.lihatSemuaKursus.visibility = View.GONE
                    binding.lihatSemuaCategory.visibility = View.GONE
                    binding.tabLayoutKursus.visibility = View.GONE
                    binding.tvKursus.visibility = View.GONE
                    fetchCourseSearch(savedToken, null, null,null,null, s.toString())
                } else {
                    binding.rvKursuspopuler.visibility = View.VISIBLE
                    binding.recycleviewCategory.visibility = View.VISIBLE
                    binding.tvKategorys.visibility = View.VISIBLE
                    binding.lihatSemuaCategory.visibility = View.VISIBLE
                    binding.tvKursus.visibility = View.VISIBLE
                    binding.lihatSemuaKursus.visibility = View.VISIBLE
                    binding.tabLayoutKursus.visibility = View.VISIBLE
                    binding.notFounds.visibility = View.GONE
                    binding.rvSearch.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun fetchCourseSearch(token: String?,rating: Double?,level: String?,category: String?, type: String?, search: String?) {
        viewMode.getFilterCourse(token,rating, level,category, type, search).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    val dataLength = it.data?.data?.size
                    if (dataLength!! < 1) {
                        binding.notFounds.visibility = View.VISIBLE
                        binding.rvSearch.visibility = View.GONE
                    }else{
                        showListHorizontal2(it.data)
                        binding.rvSearch.visibility = View.VISIBLE
                        binding.notFounds.visibility = View.GONE

                    }
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

    private fun showListHorizontal2(data: ListResponse?) {
        val adapter = AdapterKursusPopuler2(this)

        adapter.sendList(data?.data ?: emptyList())
        binding.rvSearch.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvSearch.adapter = adapter
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}