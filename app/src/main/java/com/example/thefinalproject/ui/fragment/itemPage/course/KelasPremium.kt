package com.example.thefinalproject.ui.fragment.itemPage.course

import android.annotation.SuppressLint
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
import com.example.thefinalproject.adapter.CourseAdapter
import com.example.thefinalproject.databinding.FragmentKelasFreeBinding
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.network.model.course.DataCategory
import com.example.thefinalproject.network.model.course.ListResponse
import com.example.thefinalproject.ui.fragment.botsheet.BotSheetLogin
import com.example.thefinalproject.ui.fragment.botsheet.BotsheetSelangkah

import com.example.thefinalproject.util.SharePref
import com.example.thefinalproject.util.Status
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.android.ext.android.inject


@Suppress("SameParameterValue")
class KelasPremium : Fragment(), CourseAdapter.CourseItemClickListener {
    private var _binding: FragmentKelasFreeBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharePref: SharePref
    private val viewMode : ViewModelAll by inject()
    private var categorys: List<DataCategory> = emptyList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKelasFreeBinding.inflate(layoutInflater, container, false)
        sharePref = SharePref
        fetchList(null,null,null,"premium",null)
        return binding.root

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
    private fun showBottomSheetDialog() {
        val bsDialog = BottomSheetDialog(requireContext())
        val bottomSheetView = layoutInflater.inflate(R.layout.botsheet_filtering_class, null)


        bsDialog.setContentView(bottomSheetView)
        bsDialog.show()



    }
    @SuppressLint("SuspiciousIndentation")
    private fun showListHorizontal(data: ListResponse?) {


        val adapter = CourseAdapter(this)

        adapter.sendList(data?.data ?: emptyList())

        binding.rvCourse.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.rvCourse.adapter = adapter


    }

    override fun onCourseItemClick(data: DataCategory) {
        val bundle = Bundle().apply {
            putString("selectedId", data.id)
        }
        val isLogin = SharePref.getPref(SharePref.Enum.PREF_NAME.value)
        if (isLogin != null) {
            if(data.type == "premium") {
                val bottomSheetSelangkah = BotsheetSelangkah()
                bottomSheetSelangkah.setCourseId(bundle)
                bottomSheetSelangkah.show(childFragmentManager, bottomSheetSelangkah.tag)
            }
        }else {
            val bottomSheetFragmentMustLogin = BotSheetLogin()
            bottomSheetFragmentMustLogin.show(childFragmentManager, bottomSheetFragmentMustLogin.tag)
        }


    }
}


