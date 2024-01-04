package com.example.thefinalproject.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.thefinalproject.adapter.AdapterKursusPopuler2
import com.example.thefinalproject.adapter.adapterSearch.MyClassAdapter
import com.example.thefinalproject.databinding.FragmentMyClassBinding
import com.example.thefinalproject.mvvm.viewmmodel.AuthViewModel
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.network.model.course.CategoryResponse
import com.example.thefinalproject.network.model.course.DataCategory
import com.example.thefinalproject.network.model.course.ListResponse
import com.example.thefinalproject.network.model.mycourse.Course
import com.example.thefinalproject.network.model.mycourse.MyCourseResponse
import com.example.thefinalproject.ui.fragment.botsheet.BotSheetLogin
import com.example.thefinalproject.ui.fragment.botsheet.BotsheetSelangkah
import com.example.thefinalproject.util.SharePref
import com.example.thefinalproject.util.Status
import org.koin.android.ext.android.inject

@Suppress("SameParameterValue")
class MyClassFragment : Fragment(), MyClassAdapter.ClassClick,AdapterKursusPopuler2.CourseClick {
    private var _binding: FragmentMyClassBinding? = null
    private val binding get() = _binding!!
    private val viewMode: ViewModelAll by inject()
    private lateinit var sharePref: SharePref
    private val authViewModel: AuthViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyClassBinding.inflate(layoutInflater, container, false)
        sharePref = SharePref


        fetchCategory(null)
        featureSearch()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val savedToken = sharePref.getPref(SharePref.Enum.PREF_NAME.value)

        fetchMyCourse(savedToken,null, null)

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




    private fun fetchMyCourse(token: String?,search: String?,level: String?) {
        authViewModel.myCourse(token,search,level).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    showListCourse(it.data)
                    Log.d("fetchCoursesucces", it.message.toString())

                }

                Status.ERROR -> {
                    Log.e("fetcherror", it.message.toString())
                }

                Status.LOADING -> {
                    Log.d("fetchload", it.message.toString())
                }
            }
        }
    }



    private fun showListCourse(data: MyCourseResponse?) {
        val adapter = MyClassAdapter (this)

        data?.data?.let { dataMyCourse ->
            val courses = dataMyCourse.courses ?: emptyList()
            if (courses.isEmpty()) {
                // Jika data kosong, atur visibility kelasKosong menjadi VISIBLE
                binding.kelasKosong.visibility = View.VISIBLE
                binding.ivLogo.visibility = View.VISIBLE
                binding.tvUps.visibility = View.VISIBLE
                binding.carikelas.visibility = View.VISIBLE
                binding.carikelas.setOnClickListener {
                    findNavController().navigate(R.id.action_myClassFragment2_to_myCourseFragment2)

                }
            } else {
                // Jika data tidak kosong, atur visibility kelasKosong menjadi GONE
                binding.kelasKosong.visibility = View.GONE
                binding.ivLogo.visibility = View.GONE
                binding.tvUps.visibility = View.GONE
                binding.carikelas.visibility = View.GONE
            }
            adapter.submitList(courses)
        }

        binding.rvClassBerjalan.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvClassBerjalan.adapter = adapter
    }

    private fun featureSearch(){
        val searchEt = binding.etSearch
        val savedToken = SharePref.getPref(SharePref.Enum.PREF_NAME.value)
        searchEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    binding.rvCategory.visibility = View.VISIBLE
                    binding.rvClassBerjalan.visibility = View.VISIBLE
                    binding.tv1.visibility = View.VISIBLE
                    binding.kelasKosong.visibility = View.VISIBLE
                    binding.tvCategory.visibility = View.VISIBLE
                    binding.tabLayoutClass.visibility = View.VISIBLE
                    binding.rvSearch.visibility = View.GONE
                    binding.notFounds.visibility = View.GONE
                } else if (s.length >= 3) {
                    binding.rvCategory.visibility = View.GONE
                    binding.rvClassBerjalan.visibility = View.GONE
                    binding.tvCategory.visibility = View.GONE
                    binding.tv1.visibility = View.GONE
                    binding.kelasKosong.visibility = View.GONE
                    binding.tabLayoutClass.visibility = View.GONE
                    fetchCourseSearch1(savedToken, null, null,null,null, s.toString(),null)
                } else {
                    binding.rvCategory.visibility = View.VISIBLE
                    binding.rvClassBerjalan.visibility = View.VISIBLE
                    binding.tv1.visibility = View.VISIBLE
                    binding.tvCategory.visibility = View.VISIBLE
                    binding.kelasKosong.visibility = View.VISIBLE
                    binding.tabLayoutClass.visibility = View.VISIBLE
                    binding.notFounds.visibility = View.GONE
                    binding.rvSearch.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }


    private fun fetchCourseSearch1(token: String?,rating: Double?,level: String?,category: String?, type: String?, search: String?, createAt: String?) {
        viewMode.getFilterCourse(token,rating, level,category, type, search,createAt).observe(viewLifecycleOwner) {
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
                    Log.e("error", it.message.toString())
                }

                Status.LOADING -> {
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


    override fun onCourseItemClick(data: Course) {
        val bundle = Bundle()
        bundle.putString("selectedId", data.id)
        findNavController().navigate(R.id.detailCourse,bundle)
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
}