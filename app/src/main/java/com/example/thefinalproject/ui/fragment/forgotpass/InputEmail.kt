package com.example.thefinalproject.ui.fragment.forgotpass

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.thefinalproject.R
import com.example.thefinalproject.databinding.FragmentInputEmailBinding
import com.example.thefinalproject.mvvm.viewmmodel.AuthViewModel
import com.example.thefinalproject.network.model.user.forgotpassword.postdata.PostForgotPassRequest
import com.example.thefinalproject.ui.activity.LoginActivity
import com.example.thefinalproject.ui.activity.RegisterActivity
import com.example.thefinalproject.util.Status
import org.koin.android.ext.android.inject


class InputEmail : Fragment() {
    private lateinit var binding:FragmentInputEmailBinding
    private val viewmodel: AuthViewModel by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInputEmailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            val intent = Intent (getActivity(), LoginActivity::class.java)
            getActivity()?.startActivity(intent)
        }

        binding.btnForgotPass.setOnClickListener {
            val email = binding.etEmailForgot.text.toString()
            if (email.isBlank()){
                binding.etEmailForgot.error = "Email harus diisi"
                binding.etEmailForgot.requestFocus()
                return@setOnClickListener

            }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.etEmailForgot.error = "Email tidak valid"
                binding.etEmailForgot.requestFocus()
                return@setOnClickListener
            }else{
                val bundle = Bundle()
                bundle.putString("emailForgotPass", email)
//                 Ganti dengan nama fragment tujuan
                val nextFragment = ResetPassword()

                nextFragment.arguments = bundle


//                 Ganti dengan ID kontainer di mana Anda ingin menambahkan atau mengganti fragment
                val containerId = R.id.container3

//                 Lakukan transaksi fragment
                val fragmentManager = requireActivity().supportFragmentManager
                val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(containerId, nextFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()



            }
        }
    }


}