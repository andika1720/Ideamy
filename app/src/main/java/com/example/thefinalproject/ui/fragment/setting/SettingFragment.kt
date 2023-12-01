package com.example.thefinalproject.ui.fragment.setting

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thefinalproject.R
import androidx.navigation.fragment.findNavController
import com.example.thefinalproject.ui.activity.LoginActivity


class SettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.findViewById<View>(R.id.layoutProfile).setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment2_to_myProfileFragment)
        }

        view.findViewById<View>(R.id.layoutChangePassword).setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment2_to_changePasswordFragment)
        }

        view.findViewById<View>(R.id.layoutPaymentHistory).setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment2_to_historyPaymentFragment)
        }

        view.findViewById<View>(R.id.layoutLogout).setOnClickListener {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            activity?.finish()
        }
    }
}
