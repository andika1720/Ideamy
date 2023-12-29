package com.example.thefinalproject.ui.fragment.botsheet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.thefinalproject.R
import com.example.thefinalproject.databinding.BotsheetBelicourseDetailcourseBinding
import com.example.thefinalproject.mvvm.viewmmodel.AuthViewModel
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.network.model.course.DataCourseById
import com.example.thefinalproject.network.model.course.DetailResponse
import com.example.thefinalproject.network.model.mycourse.Course
import com.example.thefinalproject.network.model.order.DataPost
import com.example.thefinalproject.network.model.order.PostResponse

import com.example.thefinalproject.util.SharePref
import com.example.thefinalproject.util.Status
import com.example.thefinalproject.util.Utils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.inject

class BotsheetSelangkah: BottomSheetDialogFragment() {

    private var _binding: BotsheetBelicourseDetailcourseBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharePref: SharePref
    private var courseId: String? = null
    private val viewModel: ViewModelAll by inject()
    private val authViewModel: AuthViewModel by inject()

    fun setCourseId(courseId: Any) {
        this.courseId = courseId.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BotsheetBelicourseDetailcourseBinding.inflate(inflater, container, false)

        sharePref = SharePref
        binding.icClose.setOnClickListener {
           dismiss()
        }

        val savedToken = SharePref.getPref(SharePref.Enum.PREF_NAME.value)

        postOrderCoroutines(savedToken.toString(),courseId ?: "")
        showDetailCoroutines(savedToken.toString(), courseId ?: "")

        return binding.root
    }



    private fun showData(data: DetailResponse) {
        val courseData: DataCourseById? = data.data

        binding.tvCategoryCourse.text = courseData?.category
        binding.tvTopicCourse.text = courseData?.title
        binding.types.text = courseData?.type
        binding.tvAuthorCourse.text = courseData?.creator
        if(courseData?.type == "premium"){
            val hargaAwal: Int? = courseData?.price
            binding.textView8.text = "${Utils.formatCurrency(hargaAwal)}"
        }else{
            val hargaFree: Int = 0
            binding.textView8.text = "${Utils.formatCurrency(hargaFree)}"
        }
        binding.tvLevel.text = "${courseData?.level} Level"
        binding.tvWaktucourse.text = "${courseData?.totalDuration} Menit"
        binding.tvModule.text = "${courseData?.totalModule} Modul"
        Glide.with(this)
            .load(courseData?.image)
            .fitCenter()
            .into(binding.imageView2)

    }

    private fun showDetailCoroutines(token:String?,id: String) {

        viewModel.getDataById("Bearer $token",id).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { data -> showData(data) }
                }
                Status.ERROR -> {
                    Log.d("Error", it.data.toString())
                }
                Status.LOADING -> {
                    Log.d("load", it.data.toString())
                }
            }
        }
    }


    private fun postOrder(data: PostResponse) {
        val courseData: DataPost? = data.data
        val bundle = Bundle().apply {
            putString("selectedId", courseData?.courseId)
            putString("orderId",data.data?.id)
        }
        binding.btnBeliSekarang.setOnClickListener {
            findNavController().navigate(R.id.detailPaymentFragment,bundle)
            dismiss()
        }


    }

    private fun postOrderCoroutines(token:String?,id: String) {

        authViewModel.ordersId("Bearer $token",id).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { data -> postOrder(data) }
                }
                Status.ERROR -> {
                    Log.d("ErrorPost", it.data.toString())
                }
                Status.LOADING -> {
                    Log.d("loadPost", it.data.toString())
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun dismissBottomSheet() {
        dismissAllowingStateLoss()
    }
}