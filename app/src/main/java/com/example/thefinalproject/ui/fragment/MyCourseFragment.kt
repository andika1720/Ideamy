package com.example.thefinalproject.ui.fragment


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
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
import com.google.android.material.bottomsheet.BottomSheetDialog

import com.google.android.material.tabs.TabLayout

import org.koin.android.ext.android.inject

@Suppress("SameParameterValue")
class MyCourseFragment : Fragment(), AdapterMyCourseNew.CourseClick {
    private lateinit var sharePref: SharePref
    private var _binding: FragmentMyCourseBinding? = null
    private val binding get() = _binding!!
    private val viewMode : ViewModelAll by inject()
    private var categorys: List<DataCategory> = emptyList()
    private var valueAllCheckbox = ArrayList<String>()
    private var valueCheckBoxCategory = ArrayList<String>()
    private var valueCheckBoxLevel = ArrayList<String>()
    private lateinit var adapter:AdapterMyCourseNew



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyCourseBinding.inflate(layoutInflater,container,false)
        sharePref = SharePref

        tabLayout()
        featureSearch()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val savedToken = SharePref.getPref(SharePref.Enum.PREF_NAME.value)
        fetchList(savedToken,null,null,null,null,null)
        binding.tabLayoutKursus.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        fetchList(savedToken,null,null,null,null,null)
                    }
                    1 -> {
                        fetchList(savedToken,null,null,null,"premium",null)
                    }
                    else -> {
                        fetchList(savedToken,null,null,null,"free",null)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
        binding.tvFilterKursus.setOnClickListener {
            botSheetFilter()
        }

    }

    private fun showListHorizontal(data: ListResponse?) {
        val adapter = AdapterMyCourseNew(this)
        adapter.sendList(data?.data ?: emptyList())
        adapter.clearAndAddData(data?.data ?: emptyList())
        binding.rvCourse.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
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

    private fun fetchList(token:String?,id: String?,level: String?,category: String?, type: String?, search: String?) {
        viewMode.getFilterCourse(token,id, level,category, type, search).observe(viewLifecycleOwner) {
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
            if (data.statusPayment) {
                // Jika statusPayment true, pindah ke navigation detailCourse
                findNavController().navigate(R.id.action_myCourseFragment2_to_detailCourse, bundle)
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
        searchEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    binding.rvCourse.visibility = View.VISIBLE
                    binding.tv1Topic.visibility = View.VISIBLE
                    binding.tvFilterKursus.visibility = View.VISIBLE
                    binding.tabLayoutKursus.visibility = View.VISIBLE
                    binding.rvSearch.visibility = View.GONE
                    binding.notFounds.visibility = View.GONE
                } else if (s.length >= 2) {
                    binding.rvCourse.visibility = View.GONE
                    binding.tvFilterKursus.visibility = View.GONE
                    binding.tv1Topic.visibility = View.GONE
                    binding.tabLayoutKursus.visibility = View.GONE
                    binding.notFounds.visibility = View.GONE
                    fetchListSearch(savedToken, null, null,null,null, s.toString())
                } else {
                    binding.rvCourse.visibility = View.VISIBLE
                    binding.tv1Topic.visibility = View.VISIBLE
                    binding.tvFilterKursus.visibility = View.VISIBLE
                    binding.tabLayoutKursus.visibility = View.VISIBLE
                    binding.notFounds.visibility = View.GONE
                    binding.rvSearch.visibility = View.GONE

                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun fetchListSearch(token:String?,id: String?,level: String?,category: String?, type: String?, search: String?) {
        viewMode.getFilterCourse(token,id, level,category, type, search).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    val length = it.data?.data?.size
                    if (length!! < 1) {
                        binding.notFounds.visibility = View.VISIBLE
                        binding.rvSearch.visibility = View.GONE
                    }else{
                        showListHorizontalSearch(it.data)
                        binding.rvSearch.visibility = View.VISIBLE
                        binding.notFounds.visibility = View.GONE

                    }
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

    private fun showListHorizontalSearch(data: ListResponse?) {
        val adapter = AdapterMyCourseNew(this)
        adapter.sendList(data?.data ?: emptyList())
        binding.rvSearch.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.rvSearch.adapter = adapter
    }
    private fun botSheetFilter(){
        try {
            val dialog = BottomSheetDialog(requireContext())
            val view = layoutInflater.inflate(R.layout.filtering_coba,null)
            val btnNext = view.findViewById<Button>(R.id.btn_filteringCourse)
            val btnClose = view.findViewById<ImageView>(R.id.btn_closes)

            btnClose.setOnClickListener {
                dialog.dismiss()
            }
            var category1 = ""
            var level1 = ""
            val radioGroup = view.findViewById<RadioGroup>(R.id.filter2)
            val radioGroupLevel = view.findViewById<RadioGroup>(R.id.filter3)

            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                val selectedRadioButton: RadioButton = view.findViewById(checkedId)
                val selectedOption: String = selectedRadioButton.text.toString()
                Log.e("filter tes", selectedOption)
                category1 = selectedOption
            }

            radioGroupLevel.setOnCheckedChangeListener { group, checkedId ->
                val selectedRadioButton: RadioButton = view.findViewById(checkedId)
                val selectedOption: String = selectedRadioButton.text.toString()
                level1 = selectedOption
            }

            btnNext.setOnClickListener {
                if (level1 == ""){
                    fetchList(null,null,null,category1,null,null)
                }else if(category1 == ""){
                    fetchList(null,null,level1.toLowerCase(),null,null,null)
                }else{
                    fetchList(null,null,level1,category1,null,null)
                }
                Log.e("Isi Check Box Category",valueCheckBoxCategory.toString())
                Log.e("Isi Check Box Level",valueCheckBoxLevel.toString())
                dialog.dismiss()
            }
            dialog.setCanceledOnTouchOutside(false)
            dialog.setContentView(view)
            dialog.show()
        }catch (e: Exception) {
            Log.e("showbotFiltering", "ErrorBotsheet", e)
        }
    }

}


