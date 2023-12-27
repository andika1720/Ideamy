package com.example.thefinalproject.ui.fragment.itemPage.class1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thefinalproject.R
import com.example.thefinalproject.databinding.FragmentSemuaKelasClassBinding
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.network.model.course.DataCategory
import org.koin.android.ext.android.inject

class SemuaKelasClass : Fragment() {
    private lateinit var binding: FragmentSemuaKelasClassBinding
    private val viewMode: ViewModelAll by inject()
    private var progress: List<DataCategory> = emptyList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSemuaKelasClassBinding.inflate(layoutInflater, container, false)

        fetchList()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun fetchList() {
        TODO("Not yet implemented")
    }
}