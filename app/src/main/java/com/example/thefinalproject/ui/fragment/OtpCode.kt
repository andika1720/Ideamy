package com.example.thefinalproject.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thefinalproject.R
import com.example.thefinalproject.databinding.FragmentOtpCodeBinding
import com.example.thefinalproject.ui.activity.LoginActivity
import com.example.thefinalproject.ui.activity.RegisterActivity

class OtpCode : Fragment() {
    private lateinit var binding:FragmentOtpCodeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOtpCodeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            val intent = Intent (getActivity(), RegisterActivity::class.java)
            getActivity()?.startActivity(intent)

        }
    }
}