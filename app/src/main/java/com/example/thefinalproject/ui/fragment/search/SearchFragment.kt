package com.example.thefinalproject.ui.fragment.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thefinalproject.R
import com.example.thefinalproject.adapter.adapterSearch.AdapterKursusSearch
import com.example.thefinalproject.databinding.FragmentSearchBinding
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.network.model.course.DataCategory
import com.example.thefinalproject.network.model.course.ListResponse
import com.example.thefinalproject.ui.fragment.botsheet.BotSheetLogin
import com.example.thefinalproject.ui.fragment.botsheet.BotsheetSelangkah
import com.example.thefinalproject.util.SharePref
import com.example.thefinalproject.util.Status
import org.koin.android.ext.android.inject


@Suppress("SameParameterValue")
class SearchFragment : Fragment(), AdapterKursusSearch.CourseClick {
    private var _binding : FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewMode : ViewModelAll by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater,container,false)

        val savedToken = SharePref.getPref(SharePref.Enum.PREF_NAME.value)
        binding.etSearch.requestFocus()
        binding.imgSearch.setOnClickListener {
            hideKeyboardAndClearFocus()
            fetchList(null,null,null,null,null,binding.etSearch.text.toString())
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
    private fun showListHorizontal(data: ListResponse?) {
        val adapter = AdapterKursusSearch(this)


        adapter.sendList(data?.data ?: emptyList())
        binding.rvKursuspopuler.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.rvKursuspopuler.adapter = adapter
    }

    private fun fetchList(token:String?,id: String?,level: String?,category: String?, type: String?, search: String?) {
        viewMode.getFilterCourse(token,id, level,category, type, search).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    showListHorizontal(it.data)


                }

                Status.ERROR -> {

                    Log.e("error", it.message.toString())
                }

                Status.LOADING -> {

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}