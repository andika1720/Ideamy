package com.example.thefinalproject.ui.fragment.itemPage.class1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thefinalproject.databinding.FragmentMyClassBinding


class InProgressKelasFragment : Fragment() {
    private var _binding: FragmentMyClassBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMyClassBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}