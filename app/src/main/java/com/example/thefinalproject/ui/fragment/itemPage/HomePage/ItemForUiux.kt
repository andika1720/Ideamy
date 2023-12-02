package com.example.thefinalproject.ui.fragment.itemPage.HomePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.thefinalproject.databinding.FragmentItemSemuaKelasBinding

class ItemForUiux:Fragment() {
    private lateinit var binding: FragmentItemSemuaKelasBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentItemSemuaKelasBinding.inflate(inflater, container, false)
        return binding.root
    }
}