package com.example.thefinalproject.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.thefinalproject.R
import com.example.thefinalproject.adapter.AdapterPageFragment
import com.example.thefinalproject.adapter.CourseAdapter
import com.example.thefinalproject.databinding.FragmentMyCourseBinding
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.network.model.course.DataCategory

import com.example.thefinalproject.ui.fragment.itemPage.course.KelasFree
import com.example.thefinalproject.ui.fragment.itemPage.course.KelasPremium
import com.example.thefinalproject.ui.fragment.itemPage.course.SemuaKelasCourseFragment
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.ext.android.inject

class MyCourseFragment : Fragment(), CourseAdapter.CourseItemClickListener {

    private var _binding: FragmentMyCourseBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragmen
        _binding = FragmentMyCourseBinding.inflate(layoutInflater,container,false)
        val fragmentList = arrayListOf( SemuaKelasCourseFragment(),
            KelasPremium(),
            KelasFree()
        )
        val titleFragment = arrayListOf("Semua Kelas", "Premium", "Free")
        val adapter = AdapterPageFragment(fragmentList,this, childFragmentManager, lifecycle)
        binding.apply {
            viewpageCourse.adapter = adapter
            TabLayoutMediator(tabLayoutKursus, viewpageCourse) { tab, position ->
                tab.text = titleFragment[position]
            }.attach()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onCourseItemClick(data: DataCategory) {
        val bundle = Bundle().apply {
            putString("selectedId", data.id)
        }
        if (data.type == "premium") {
            findNavController().navigate(R.id.action_myCourseFragment2_to_detailPaymentFragment, bundle)
        } else {
            findNavController().navigate(R.id.action_myCourseFragment2_to_detailCourse, bundle)
        }
    }
    }


