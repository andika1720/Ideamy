package com.example.thefinalproject.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thefinalproject.databinding.FragmentNotifikasiBinding


class NotifikasiFragment : Fragment() {
    private lateinit var binding: FragmentNotifikasiBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNotifikasiBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setUpAdaoterNotifikasi(){

        binding.rvNotifikasi.adapter
        binding.rvNotifikasi.layoutManager = LinearLayoutManager(requireContext())
    }
}