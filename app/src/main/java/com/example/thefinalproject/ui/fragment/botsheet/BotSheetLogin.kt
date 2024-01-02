package com.example.thefinalproject.ui.fragment.botsheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thefinalproject.databinding.BotsheetHarusloginBinding
import com.example.thefinalproject.ui.activity.LoginActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BotSheetLogin: BottomSheetDialogFragment() {

    private var _binding: BotsheetHarusloginBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BotsheetHarusloginBinding.inflate(inflater, container, false)

        binding.closeBotsheetLog.setOnClickListener {
            dismiss()
        }

        binding.btnToLoginBotshet.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}