package com.example.thefinalproject.ui.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
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

//    private fun showListHorizontal(data: ListResponse?) {
//        val adapter = AdapterMyCourseNew(this)
//        adapter.sendList(data?.data ?: emptyList())
//        binding.rvCourse.layoutManager =
//            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
//        binding.rvCourse.adapter = adapter
//    }
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

    private fun botSheetFilter(){
        try {
            val dialog = BottomSheetDialog(requireContext())
            val view = layoutInflater.inflate(R.layout.filtering_coba,null)
            val btnNext = view.findViewById<Button>(R.id.btn_filteringCourse)
            val btnClose = view.findViewById<ImageView>(R.id.btn_closes)
            val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
            val checkBox1 = view.findViewById<CheckBox>(R.id.checkBox2)
            val checkBox2 = view.findViewById<CheckBox>(R.id.checkBox3)
            val checkBox3 = view.findViewById<CheckBox>(R.id.checkBox4)
            val checkBox4 = view.findViewById<CheckBox>(R.id.checkBox5)
            val checkBox5 = view.findViewById<CheckBox>(R.id.checkBox6)
            val checkBox6 = view.findViewById<CheckBox>(R.id.checkBox7)
            val checkBox7 = view.findViewById<CheckBox>(R.id.checkBox8)
            val checkBox8 = view.findViewById<CheckBox>(R.id.checkBox9)
            val checkBox9 = view.findViewById<CheckBox>(R.id.checkBox10)
            val checkBox10 = view.findViewById<CheckBox>(R.id.checkBox11)
            val checkBox11 = view.findViewById<CheckBox>(R.id.checkBox12)
            btnClose.setOnClickListener {
                dialog.dismiss()
            }
            btnNext.setOnClickListener {
                var category1 = ""
                var level1 = ""

//                if (checkBox.isChecked){
//                    valueAllCheckbox.add(checkBox.text.toString())
//                }
//                if (checkBox1.isChecked){
//                    valueAllCheckbox.add(checkBox1.text.toString())
//                }
//                if (checkBox2.isChecked){
//                    valueAllCheckbox.add(checkBox2.text.toString())
//                }

                if (checkBox3.isChecked){
                    category1 = checkBox3.text.toString()

                }else if (checkBox4.isChecked){
                    category1 = checkBox4.text.toString()

                }else if(checkBox5.isChecked){
                    category1 = checkBox5.text.toString()

                }else if (checkBox6.isChecked){
                    category1 = checkBox6.text.toString()

                }else{
                    category1 = checkBox7.text.toString()

                }

                if (checkBox8.isChecked){
                    level1 = checkBox8.text.toString()

                }else if(checkBox9.isChecked){
                    level1 = checkBox9.text.toString()

                }else if(checkBox10.isChecked){
                    level1 = checkBox10.text.toString()

                }else{
                    level1 = checkBox11.text.toString()
                }
//                if (checkBox3.isChecked){
//                    valueCheckBoxCategory.add(checkBox3.text.toString())
//                }
//                if (checkBox4.isChecked){
//                    valueCheckBoxCategory.add(checkBox4.text.toString())
//                }
//                if (checkBox5.isChecked){
//                    valueCheckBoxCategory.add(checkBox5.text.toString())
//                }
//                if (checkBox6.isChecked){
//                    valueCheckBoxCategory.add(checkBox6.text.toString())
//                }
//                if (checkBox7.isChecked){//
//                    valueCheckBoxCategory.add(checkBox7.text.toString())
//                }
//                if (checkBox8.isChecked){
//                    valueCheckBoxLevel.add(checkBox8.text.toString())
//                }
//                if (checkBox9.isChecked){
//                    valueCheckBoxLevel.add(checkBox9.text.toString())
//                }
//                if (checkBox10.isChecked){
//                    valueCheckBoxLevel.add(checkBox10.text.toString())
//                }
//                if (checkBox11.isChecked){
//                    valueCheckBoxLevel.add(checkBox11.text.toString())
//                }
//                val adapter1 = AdapterMyCourseNew(this)
//                val filteredData = categorys.filter { data ->
//                    valueCheckBoxCategory.contains(data.category) && valueCheckBoxLevel.contains(data.level)
//                }
//
//                adapter1.updateDataset(filteredData)
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


