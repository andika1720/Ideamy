package com.example.thefinalproject.ui.fragment.forgotpass

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.thefinalproject.R
import com.example.thefinalproject.databinding.FragmentInputEmailBinding
import com.example.thefinalproject.ui.activity.LoginActivity


class InputEmail : Fragment() {
    private lateinit var binding:FragmentInputEmailBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInputEmailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            val intent = Intent (activity, LoginActivity::class.java)
            activity?.startActivity(intent)
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