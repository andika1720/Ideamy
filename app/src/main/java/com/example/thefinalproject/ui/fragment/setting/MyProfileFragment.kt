package com.example.thefinalproject.ui.fragment.setting

import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.navigation.fragment.findNavController
import com.example.thefinalproject.R
import com.example.thefinalproject.databinding.FragmentMyProfileBinding
import com.example.thefinalproject.mvvm.viewmmodel.AuthViewModel

import com.example.thefinalproject.util.SharePref

import org.koin.android.ext.android.inject

class MyProfileFragment : Fragment() {

    private var _binding : FragmentMyProfileBinding? = null


    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyProfileBinding.inflate(inflater, container, false)
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_myProfileFragment_to_settingFragment2)
        }

        return binding.root
    }

}