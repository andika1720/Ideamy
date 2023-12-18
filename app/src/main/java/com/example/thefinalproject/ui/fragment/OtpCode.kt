package com.example.thefinalproject.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
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
import com.example.thefinalproject.util.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class OtpCode : Fragment() {
    private lateinit var binding:FragmentOtpCodeBinding
    private val viewmodel: AuthViewModel by inject()
//    private val authViewModel: AuthViewModel by lazy {
//        AuthViewModel(Repository(ApiClient.instance))
//    }
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
        val email1 = getDataRegis?.email.toString()

        binding.massage.text ="Ketik 6 digit kode yang dikirimkan ke $email1"

        startCountDownTimer()
        binding.tvKirimUlang.setOnClickListener {
            startCountDownTimer()
        }


        binding.kirimOtp.setOnClickListener {
            val value1 = binding.otpBox1.text.toString()
            val value2 = binding.otpBox2.text.toString()
            val value3 = binding.otpBox3.text.toString()
            val value4 = binding.otpBox4.text.toString()
            val value5 = binding.otpBox5.text.toString()
            val value6 = binding.otpBox6.text.toString()
            val combinedValue = "$value1$value2$value3$value4$value5$value6"
            sendOtp1(OtpRequest(email1.toString(),combinedValue))
            Log.e("email ",email1)
            Log.e("otp ",combinedValue)

        }

    }
    private fun sendOtp(email: String, otp: String) {
        val otpReq = OtpRequest(email, otp)

        viewmodel.otpUser(otpReq).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "Verifikasi OTP selesai", Toast.LENGTH_SHORT).show()
                    val intent = Intent(requireActivity(), LoginActivity::class.java)
                    startActivity(intent)
                }
                Status.ERROR -> {
                    val errorMessage = resource.message ?: "Error Occurred!"
                    Log.d("errorOTP", errorMessage)
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    Log.d("load", "Loading")
                    // Tambahkan tindakan loading jika diperlukan
                }
            }
        }
    }


    fun sendOtp1(otpRequest: OtpRequest) {
        viewmodel.otpUser(otpRequest).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(),"Verifikasi OTP selesai",Toast.LENGTH_SHORT).show()
                    val intent = Intent(requireActivity(), LoginActivity::class.java)
                    startActivity(intent)
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