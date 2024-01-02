package com.example.thefinalproject.ui.fragment.setting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.thefinalproject.R
import com.example.thefinalproject.databinding.FragmentChangePasswordBinding
import com.example.thefinalproject.mvvm.viewmmodel.AuthViewModel
import com.example.thefinalproject.network.model.user.resetpassword.ChangePasswordRequest
import com.example.thefinalproject.ui.activity.LoginActivity
import com.example.thefinalproject.util.SharePref
import com.example.thefinalproject.util.Status
import com.example.thefinalproject.util.Utils
import org.koin.android.ext.android.inject


class ChangePasswordFragment : Fragment() {
    private lateinit var binding: FragmentChangePasswordBinding
    private val viewModel : AuthViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentChangePasswordBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val savedToken = SharePref.getPref(SharePref.Enum.PREF_NAME.value)
        Log.e("TOken",savedToken.toString())
        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.action_changePasswordFragment_to_settingFragment2)
        }

        binding.btnChangePassword.setOnClickListener {
            val passLama = binding.etOldPass.text.toString()
            val passBaru = binding.etNewPass.text.toString()
            val validNewPass = binding.etNewPass2.text.toString()
//            val changePasswordRequest = ChangePasswordRequest(passLama,passBaru)
            val changePasswordRequest = ChangePasswordRequest(passBaru,passLama)
            if (passLama.isBlank() || passBaru.isBlank()  || validNewPass.isBlank()) {
                Utils.toastMessage(requireContext(), "Semua field harus diisi")
            } else if(passLama == passBaru){
                binding.etNewPass.error = "Password tidak boleh sama"
                binding.etNewPass.requestFocus()
                return@setOnClickListener
            }else if (passBaru != validNewPass){
                binding.etNewPass2.error = "Harus sama dengan password baru"
                binding.etNewPass2.requestFocus()
                return@setOnClickListener
            }else{
                changePassword(savedToken.toString(),changePasswordRequest)
            }




        }


    }

    private fun changePassword(token:String, changePasswordRequest: ChangePasswordRequest){
        viewModel.resetPasswordUser(token,changePasswordRequest).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.d("changePassword", "Succes")
                    Utils.toastMessage(requireContext(), "Ubah password berhasil")
                    logout()
                }
                Status.ERROR -> {
                    Log.d("changePasswordError", it.message.toString())
                    changePassError(it.message.toString())
                }

                Status.LOADING -> {
                    Log.d("changePasswordLoad", it.data.toString())
                }
            }
        }

    }

    private fun logout() {
        SharePref.clearPrefs()
        SharePref.setLoginStatus(false)
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        activity?.finish()
    }

    private fun changePassError(message: String) {
        when {
            message.contains("HTTP 500") -> {
                Utils.toastMessage(requireContext(), "jwt expired")
            }
            message.contains("HTTP 400") -> {
                Utils.toastMessage(requireContext(), "Password lama tidak sesuai")
            }
            else -> {
                Utils.toastMessage(requireContext(), message)
            }
        }
    }
}