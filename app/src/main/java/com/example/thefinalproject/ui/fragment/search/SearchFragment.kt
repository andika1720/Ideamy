package com.example.thefinalproject.ui.fragment.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thefinalproject.R
import com.example.thefinalproject.adapter.adapterSearch.AdapterKursusSearch
import com.example.thefinalproject.adapter.adapterSearch.MyClassAdapter
import com.example.thefinalproject.databinding.FragmentSearchBinding
import com.example.thefinalproject.mvvm.viewmmodel.AuthViewModel
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
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
class SearchFragment : Fragment(), AdapterKursusSearch.CourseClick {
    private var _binding : FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharePref: SharePref
    private val authViewModel: AuthViewModel by inject()
    private val viewMode : ViewModelAll by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater,container,false)
        sharePref = SharePref
        val savedToken = sharePref.getPref(SharePref.Enum.PREF_NAME.value)
        binding.etSearch.requestFocus()
        binding.imgSearch.setOnClickListener {
            hideKeyboardAndClearFocus()
            fetchMyCourse(savedToken,null)
        }
        return binding.root
    }


    private fun hideKeyboardAndClearFocus() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // Check if the currently focused view is an EditText or has focusable property
        if (requireActivity().currentFocus is View) {
            val focusedView = requireActivity().currentFocus as View
            focusedView.clearFocus()
        }

        // Hide the keyboard
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
    private fun showListCourse(data: MyCourseResponse?) {
        val adapter = MyClassAdapter { clickedCourse ->
            navigatoToCourse(clickedCourse)
        }

        data?.data?.let { dataMyCourse ->
            val courses = dataMyCourse.courses ?: emptyList()
            if (courses.isEmpty()) {
                // Jika data kosong, atur visibility kelasKosong menjadi VISIBLE
                binding.kelasKosong.visibility = View.VISIBLE
                binding.carikelas.setOnClickListener {
                    findNavController().navigate(R.id.action_myClassFragment2_to_myCourseFragment2)

                }
            } else {
                // Jika data tidak kosong, atur visibility kelasKosong menjadi GONE
                binding.kelasKosong.visibility = View.GONE
            }
            adapter.submitList(courses)
        }

        binding.rvKursuspopuler.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvKursuspopuler.adapter = adapter
    }

    private fun fetchMyCourse(token: String?,search: String?) {
        authViewModel.myCourse(token,search).observe(viewLifecycleOwner) {
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

    override fun onCourseItemClick(data: DataCategory) {

        val bundle = Bundle().apply {
            putString("selectedId", data.id)
        }
        val isLogin = SharePref.getPref(SharePref.Enum.PREF_NAME.value)
        if (isLogin != null) {
            if (data.type == "premium") {
                val bottomSheetSelangkah = BotsheetSelangkah()
                bottomSheetSelangkah.setCourseId(bundle.getString("selectedId") ?: "")
                bottomSheetSelangkah.show(childFragmentManager, bottomSheetSelangkah.tag)
            } else if (data.type == "free") {
                findNavController().navigate(R.id.detailCourse, bundle)
            }
        } else {
            val botsheetLogin = BotSheetLogin()
            botsheetLogin.show(childFragmentManager, botsheetLogin.tag)
        }


    }
    private fun navigatoToCourse(data: Course){
        val bundle = Bundle()
        bundle.putString("selectedId", data.id)
        findNavController().navigate(R.id.detailCourse,bundle)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}