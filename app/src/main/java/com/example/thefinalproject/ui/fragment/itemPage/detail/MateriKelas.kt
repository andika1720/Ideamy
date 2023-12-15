package com.example.thefinalproject.ui.fragment.itemPage.detail

import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thefinalproject.adapter.AdapterCategory
import com.example.thefinalproject.adapter.DataAdapterDetail.CourseDetailAdapter

import com.example.thefinalproject.databinding.FragmentMateriKelasBinding
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.network.model.course.CategoryResponse
import com.example.thefinalproject.network.model.course.DetailResponse
import com.example.thefinalproject.util.CourseDetailItem
import com.example.thefinalproject.util.Status


import org.koin.android.ext.android.inject


class MateriKelas : Fragment() {
    private val viewModel: ViewModelAll by inject()
    private var _binding: FragmentMateriKelasBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CourseDetailAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMateriKelasBinding.inflate(inflater, container, false)
        val arg =arguments?.getString("selectedId")


        return binding.root
    }

}