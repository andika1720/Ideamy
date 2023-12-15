package com.example.thefinalproject.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.thefinalproject.R
import com.example.thefinalproject.databinding.FragmentOtpCodeBinding
import com.example.thefinalproject.mvvm.repository.Repository
import com.example.thefinalproject.mvvm.viewmmodel.AuthViewModel
import com.example.thefinalproject.network.api.ApiClient
import com.example.thefinalproject.network.model.user.otp.OtpRequest
import com.example.thefinalproject.network.model.user.register.RegisterRequest
import com.example.thefinalproject.ui.activity.LoginActivity
import com.example.thefinalproject.ui.activity.RegisterActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OtpCode : Fragment() {
    private lateinit var binding:FragmentOtpCodeBinding
    private val authViewModel: AuthViewModel by lazy {
        AuthViewModel(Repository(ApiClient.instance))
    }
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

        val getDataRegis = arguments?.getParcelable<RegisterRequest>("dataRegis")
        binding.massage.text ="Ketik 6 digit kode yang dikirimkan ke ${getDataRegis?.phoneNumber}"

        object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                binding.tvKirimUlang.text = "Kirim Ulang OTP Dalam $secondsRemaining detik"
            }

            override fun onFinish() {
                // Logika ketika hitungan mundur selesai
                binding.tvKirimUlang.text = "Kirim Ulang OTP Sekarang"
            }
        }.start()

        binding.tvKirimUlang.setOnClickListener {
            startCountDownTimer()
        }

        // Mendapatkan nilai dari masing-masing EditText
        val value1 = binding.otpBox1.text.toString()
        val value2 = binding.otpBox2.text.toString()
        val value3 = binding.otpBox3.text.toString()
        val value4 = binding.otpBox4.text.toString()
        val value5 = binding.otpBox5.text.toString()
        val value6 = binding.otpBox6.text.toString()
        val combinedValue = "$value1$value2$value3$value4$value5$value6"


        binding.kirimOtp.setOnClickListener {
            sendOtp(OtpRequest(getDataRegis?.email!!,combinedValue))
        }

    }

    fun sendOtp(otpRequest: OtpRequest){
        lifecycleScope.launch {
            try {
                authViewModel.otpUser(otpRequest)
                Toast.makeText(
                    requireContext(),
                    "Registration successful",
                    Toast.LENGTH_SHORT
                ).show()
                // Redirect to login screen or perform any other action
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    "Registration failed: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun startCountDownTimer() {
        object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                binding.tvKirimUlang.text = "Kirim Ulang OTP Dalam $secondsRemaining detik"
            }

            override fun onFinish() {
                binding.tvKirimUlang.text = "Kirim Ulang OTP Sekarang"
            }
        }.start()
    }
}