package com.example.thefinalproject.ui.fragment.forgotpass

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.thefinalproject.R
import com.example.thefinalproject.databinding.FragmentResetPasswordBinding
import com.example.thefinalproject.mvvm.viewmmodel.AuthViewModel
import com.example.thefinalproject.network.model.user.forgotpassword.postdata.PostForgotPassRequest
import com.example.thefinalproject.util.Status
import com.example.thefinalproject.util.Utils
import org.koin.android.ext.android.inject

class ResetPassword : Fragment() {
    private lateinit var binding: FragmentResetPasswordBinding
    private val viewmodel: AuthViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentResetPasswordBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            val fragmet = InputEmail()
            moveFragment(fragmet)

        }
        val emailForgot = arguments?.getString("emailForgotPass")
        Log.e("emailForgot",emailForgot!!)
        binding.btnChangePassword.setOnClickListener {
            val passBaru = binding.etOldPass.text.toString()
            val validNewPass = binding.etNewPass.text.toString()
            if(passBaru.length < 8 || passBaru.length > 12){
                binding.etOldPass.error = "Password harus terdiri 8-12 karakter"

            }else if (passBaru != validNewPass){
                binding.etOldPass.error = "Password tidak sama"
                binding.etNewPass.error = "Password tidak sama"
            }else{
//                mbundle.putString("passwordForgot",passBaru)
//                mbundle.putString("emailnext",emailForgot)
                forgotPassword(emailForgot,passBaru)
            }
        }
    }
    private fun forgotPassword(email:String, password:String){
        viewmodel.forgotPassword(PostForgotPassRequest(email)).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    Utils.toastMessage(requireContext(), "OTP Dikirim Ke email")
                    val fragment = OtpForgotPassword()
                    val bundle = Bundle()
                    bundle.putString("emailForgot1",email)
                    bundle.putString("passwordForgot",password)
                    fragment.arguments = bundle
                    val containerId = R.id.container3
                    val fragmentManager = requireActivity().supportFragmentManager
                    val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(containerId, fragment)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
//                    moveFragment(fragment)
                }
                Status.ERROR -> {
                    val errorMessage = it.message ?: "Error Occurred!"
                    Log.d("errorOTP", errorMessage)
                    resetPassError(it.message.toString())
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

    private fun resetPassError(message: String) {
        when {
            message.contains("HTTP 500") -> {
                Utils.toastMessage(requireContext(), "Email tidak valid")
            }
            message.contains("HTTP 400") -> {
                Utils.toastMessage(requireContext(), "Email tidak valid")
            }
            else -> {
                Utils.toastMessage(requireContext(), message)
            }
        }
    }
}