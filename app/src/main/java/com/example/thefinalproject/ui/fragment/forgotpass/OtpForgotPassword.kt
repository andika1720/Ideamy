package com.example.thefinalproject.ui.fragment.forgotpass

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.thefinalproject.R
import com.example.thefinalproject.databinding.FragmentOtpForgotPasswordBinding
import com.example.thefinalproject.mvvm.viewmmodel.AuthViewModel
import com.example.thefinalproject.network.model.user.forgotpassword.putdata.PutForgotPassRequest
import com.example.thefinalproject.ui.activity.LoginActivity
import com.example.thefinalproject.util.Status
import com.example.thefinalproject.util.Utils
import org.koin.android.ext.android.inject


class OtpForgotPassword : Fragment() {
    private lateinit var binding: FragmentOtpForgotPasswordBinding
    private val viewmodel: AuthViewModel by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOtpForgotPasswordBinding.inflate(inflater,container,false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            val fragment = ResetPassword()
            moveFragment(fragment)

        }

        val emailForgote = arguments?.getString("emailForgot1")
        val password = arguments?.getString("passwordForgot")
        binding.massage.text = "Ketik 6 digit kode yang dikirimkan ke $emailForgote"
        binding.kirimOtp.setOnClickListener {
            val value1 = binding.otpBox1.text.toString()
            val value2 = binding.otpBox2.text.toString()
            val value3 = binding.otpBox3.text.toString()
            val value4 = binding.otpBox4.text.toString()
            val value5 = binding.otpBox5.text.toString()
            val value6 = binding.otpBox6.text.toString()
            val combinedValue = "$value1$value2$value3$value4$value5$value6"
            // Check if any OTP box is empty
            if (value1.isEmpty() || value2.isEmpty() || value3.isEmpty() || value4.isEmpty() || value5.isEmpty() || value6.isEmpty()) {
                Utils.toastMessage(requireContext(), "Mohon masukan semua kode OTP")
            } else {
                checkForgotPassword(PutForgotPassRequest(emailForgote,combinedValue,password))
            }

        }

    }

    private fun checkForgotPassword(putForgotPassRequest: PutForgotPassRequest) {
        viewmodel.putForgotPassword(putForgotPassRequest).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    Utils.toastMessage(requireContext(), "Password Berhasil Di Ubah")
                    val intent = Intent (activity, LoginActivity::class.java)
                    activity?.startActivity(intent)

                }
                Status.ERROR -> {
                    val errorMessage = it.message ?: "Error Occurred!"
                    Log.d("errorOTP", errorMessage)
                    Utils.toastMessage(requireContext(), errorMessage)
                }
                Status.LOADING -> {
                    Log.d("load", "Loading")
                }
            }
        }

    }
    private fun moveFragment(fragment:Fragment) {
        val bundle = Bundle()
//    Ganti dengan nama fragment tujuan
        fragment.arguments = bundle
//    Ganti dengan ID kontainer di mana Anda ingin menambahkan atau mengganti fragment
        val containerId = R.id.container3
//    Lakukan transaksi fragment
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(containerId, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}