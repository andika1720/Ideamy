package com.example.thefinalproject.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.thefinalproject.R
import com.example.thefinalproject.databinding.FragmentOtpCodeBinding
import com.example.thefinalproject.mvvm.viewmmodel.AuthViewModel
import com.example.thefinalproject.network.model.user.otp.OtpRequest
import com.example.thefinalproject.network.model.user.otp.resendotp.ResendOtpRequest
import com.example.thefinalproject.network.model.user.register.RegisterRequest
import com.example.thefinalproject.ui.activity.LoginActivity
import com.example.thefinalproject.ui.activity.RegisterActivity
import com.example.thefinalproject.util.Status
import com.example.thefinalproject.util.Utils
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.android.ext.android.inject

@Suppress("DEPRECATION")
class OtpCode : Fragment() {
    private lateinit var binding:FragmentOtpCodeBinding
    private lateinit var hiddenTextView1: TextView
    private lateinit var hiddenTextView2: TextView
    private val viewmodel: AuthViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

        startCountDownTimer(email1)


        binding.kirimOtp.setOnClickListener {
            val value1 = binding.otpBox1.text.toString()
            val value2 = binding.otpBox2.text.toString()
            val value3 = binding.otpBox3.text.toString()
            val value4 = binding.otpBox4.text.toString()
            val value5 = binding.otpBox5.text.toString()
            val value6 = binding.otpBox6.text.toString()
            // Check if any OTP box is empty
            if (value1.isEmpty() || value2.isEmpty() || value3.isEmpty() || value4.isEmpty() || value5.isEmpty() || value6.isEmpty()) {
                Utils.toastMessage(requireContext(), "Mohon Masukkan semua kode OTP")
            } else {
                val combinedValue = "$value1$value2$value3$value4$value5$value6"
                sendOtp1(OtpRequest(email1, combinedValue))
                Log.e("email ", email1)
                Log.e("otp ", combinedValue)
            }

        }

    }



    fun sendOtp1(otpRequest: OtpRequest) {
        viewmodel.otpUser(otpRequest).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    Utils.toastMessage(requireContext(), "Verifikasi OTP selesai")
                    botSheetRegistSuccess()
                }
                Status.ERROR -> {
                    val errorMessage = it.message ?: "Error Occurred!"
                    Log.d("errorOTP", errorMessage)
                    otpError(errorMessage)
                }
                Status.LOADING -> {
                    Log.d("load", "Loading")
                }
            }
        }
    }

    private fun startCountDownTimer(email: String) {
        object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = (millisUntilFinished / 1000) % 60
                binding.tvKirimUlang.text = "Kirim Ulang OTP Dalam $seconds detik"
            }
            override fun onFinish() {
                hiddenTextView1 = view?.findViewById(R.id.tv_kirimUlang)!!
                hiddenTextView2 = view?.findViewById(R.id.requestCodeEmail)!!
                hiddenTextView1.visibility = View.INVISIBLE
                hiddenTextView2.visibility = View.VISIBLE

                binding.requestCodeEmail.setOnClickListener {
                    if (hiddenTextView2.visibility == View.VISIBLE) {
                        hiddenTextView2.visibility = View.INVISIBLE
                        hiddenTextView1.visibility = View.VISIBLE
                        startCountDownTimer(email)
                    }
                    resendOtp(ResendOtpRequest(email))
                }
            }
        }.start()
    }

    private fun botSheetRegistSuccess(){
        try {
            val dialog = BottomSheetDialog(requireContext())
            val view = layoutInflater.inflate(R.layout.botsheet_registrasi_berhasil,null)
            val btnNext = view.findViewById<Button>(R.id.btn_to_home_botshet)
            val btnClose = view.findViewById<ImageView>(R.id.close_botsheet_regis)
            btnNext.setOnClickListener {
                dialog.dismiss()
                val intent = Intent(requireActivity(), LoginActivity::class.java)
                startActivity(intent)
            }
            btnClose.setOnClickListener {
                dialog.dismiss()
            }
            dialog.setCanceledOnTouchOutside(false)
            dialog.setContentView(view)
            dialog.show()
        }catch (e: Exception) {
            Log.e("showbotPayment", "ErrorBotsheet", e)
        }
    }

    fun resendOtp(resendOtpRequest: ResendOtpRequest) {
        viewmodel.resendOtpUser(resendOtpRequest).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    Utils.toastMessage(requireContext(), "Otp berhasil di kirim ulang")
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

    private fun otpError(message: String) {
        when {
            message.contains("HTTP 500") -> {
                Utils.toastMessage(requireContext(), "Kode OTP expired")
            }
            message.contains("HTTP 400") -> {
                Utils.toastMessage(requireContext(), "Masukkan kode OTP dengan benar")
            }
            // Tambahkan penanganan error lain sesuai kebutuhan
            else -> {
                Utils.toastMessage(requireContext(), message)
            }
        }
    }
}