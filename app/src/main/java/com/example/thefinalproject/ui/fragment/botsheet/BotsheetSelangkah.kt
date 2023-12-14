package com.example.thefinalproject.ui.fragment.botsheet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.thefinalproject.databinding.BotsheetBelicourseDetailcourseBinding
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.network.model.course.DataCourseById
import com.example.thefinalproject.network.model.course.DetailResponse
import com.example.thefinalproject.ui.fragment.DetailPaymentFragment
import com.example.thefinalproject.util.Status
import com.example.thefinalproject.util.Utils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.inject

class BotsheetSelangkah: BottomSheetDialogFragment() {
    private var _binding: BotsheetBelicourseDetailcourseBinding? = null
    private val binding get() = _binding!!

    private var courseId: String? = null
    private val viewModel: ViewModelAll by inject()

    fun setCourseId(courseId: Any) {
        this.courseId = courseId.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BotsheetBelicourseDetailcourseBinding.inflate(inflater, container, false)

        binding.icClose.setOnClickListener {
           dismiss()
        }

        binding.btnBeliSekarang.setOnClickListener {
            val intent = Intent(requireContext(), DetailPaymentFragment::class.java)
            intent.putExtra("selectedId", courseId)
            startActivity(intent)
        }

        showDetailCoroutines(courseId.toString())
        return binding.root
    }



    private fun showData(data: DetailResponse) {
        val courseData: DataCourseById? = data.data
        val hargaAwal: Int? = courseData?.price

        binding.tvCategoryCourse.text = courseData?.category
        binding.tvTopicCourse.text = courseData?.title
        binding.tvAuthorCourse.text = courseData?.creator
        binding.textView8.text = "${Utils.formatCurrency(hargaAwal)}"
        binding.tvLevel.text = "${courseData?.level} Level"
        binding.tvWaktucourse.text = "${courseData?.totalDuration} Menit"
        binding.tvModule.text = "${courseData?.totalModule} Modul"
        Glide.with(this)
            .load(courseData?.image)
            .fitCenter()
            .into(binding.imageView2)
    }

    private fun showDetailCoroutines(id: String) {

        viewModel.getDataById(id).observe(viewLifecycleOwner) {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}