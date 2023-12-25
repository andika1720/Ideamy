package com.example.thefinalproject.ui.fragment.forgotpass

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.thefinalproject.R
import com.example.thefinalproject.databinding.FragmentInputEmailBinding
import com.example.thefinalproject.databinding.FragmentOtpForgotPasswordBinding
import com.example.thefinalproject.mvvm.viewmmodel.AuthViewModel
import com.example.thefinalproject.network.model.user.forgotpassword.putdata.PutForgotPassRequest
import com.example.thefinalproject.network.model.user.otp.OtpRequest
import com.example.thefinalproject.network.model.user.otp.resendotp.ResendOtpRequest
import com.example.thefinalproject.ui.activity.LoginActivity
import com.example.thefinalproject.util.Status
import org.koin.android.ext.android.inject


class OtpForgotPassword : Fragment() {
    private lateinit var binding: FragmentOtpForgotPasswordBinding
    private lateinit var hiddenTextView1: TextView
    private lateinit var hiddenTextView2: TextView
    private val viewmodel: AuthViewModel by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOtpForgotPasswordBinding.inflate(inflater,container,false)
        return binding.root
    }

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
                Toast.makeText(requireContext(), "Mohon masukan semua kode OTP", Toast.LENGTH_SHORT).show()
            } else {
                checkForgotPassword(PutForgotPassRequest(emailForgote,combinedValue,password))
            }

        }

    }

    private fun checkForgotPassword(putForgotPassRequest: PutForgotPassRequest) {
        viewmodel.putForgotPassword(putForgotPassRequest).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(),"Password Berhasil Di Ubah", Toast.LENGTH_SHORT).show()
                    val intent = Intent (getActivity(), LoginActivity::class.java)
                    getActivity()?.startActivity(intent)

                }
                Status.ERROR -> {
                    val errorMessage = it.message ?: "Error Occurred!"
                    Log.d("errorOTP", errorMessage)
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    Log.d("load", "Loading")
                }
            }
        }

    }
    fun moveFragment(fragment:Fragment){
        val bundle = Bundle()
//    Ganti dengan nama fragment tujuan
        val nextFragment = fragment
        nextFragment.arguments = bundle
//    Ganti dengan ID kontainer di mana Anda ingin menambahkan atau mengganti fragment
        val containerId = R.id.container3
//    Lakukan transaksi fragment
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(containerId, nextFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}