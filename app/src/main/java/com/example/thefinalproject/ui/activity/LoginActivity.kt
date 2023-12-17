package com.example.thefinalproject.ui.activity

import SharePref
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.thefinalproject.R
import com.example.thefinalproject.databinding.ActivityLoginBinding
import com.example.thefinalproject.mvvm.viewmmodel.AuthViewModel
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.network.model.user.login.LoginRequest
import com.example.thefinalproject.util.Status
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.ext.android.inject


class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private val viewmodel: AuthViewModel by inject()
    private lateinit var sharePref: SharePref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharePref = SharePref
        binding.masukWithoutLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        binding.daftardisiniLogin.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))

        }
        binding.btnMasukLogin.setOnClickListener {
            val emailText = binding.etEmailLogin.text.toString()
            val passwordText = binding.etPasswordRegis.text.toString()
            login(emailText,passwordText)

        }


//            if (emailText.isBlank() || passwordText.isBlank()) {
//                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
//                // Fokus pada bidang nama jika belum terisi
//                if (emailText.isBlank()) {
//                    binding.etEmailLogin.requestFocus()
//                }
//            }else if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
//                    binding.etEmailLogin.error = "Email Tidak Valid"
//                    binding.etEmailLogin.requestFocus()
//                    return@setOnClickListener
//            } else{
//                startActivity(Intent(this,MainActivity::class.java))
//            }


    }

    private fun login(email: String, password: String) {
        if (emailValid(binding.etEmailLogin, binding.textInputEmailRegis) &&
            passwordValid(binding.etPasswordRegis, binding.textInputPasswordLogin)
        ) {
            val loginReq = LoginRequest(email, password)
            viewmodel.loginUser(loginReq).observe(this) {
                when (it.status) {
                    Status.SUCCESS -> {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                        val token = it.data?.data?.token
                        sharePref.setPref(SharePref.Enum.PREF_NAME.value, token)
                        Log.d("LoginBerhasil", "Token= $token")


                            Log.d("LoginActivity12", "Before starting MainActivity")

                            Log.d("LoginActivity12", "After starting MainActivity")


                    }
                    Status.ERROR -> {
                        val errorMessage = it.message ?: "Error Occurred!"
                        Log.d("errorLogin", errorMessage)

                            loginError(errorMessage)


                    }
                    Status.LOADING -> {
                        Log.d("load", "Loading")
                    }
                }
            }
        }
    }
    private fun emailValid(edEmail: EditText, emailTxf: TextInputLayout): Boolean {
        val email = edEmail.text.toString().trim()

        return when {
            email.isEmpty() -> {
                emailTxf.error = "Please fill in all fields"
                false
            }
            !isValidEmail(email) -> {
                emailTxf.error = "Email harus unik dan valid"
                false
            }
            else -> {
                emailTxf.error = null
                true
            }
        }
    }
    private fun passwordValid(edPass: EditText, passTxf: TextInputLayout): Boolean {
        return when {
            edPass.text.toString().trim().isEmpty() -> {
                passTxf.error = "Password tidak boleh kosong"
                false
            }
            edPass.text.toString().trim().length < 8 -> {
                passTxf.error = "Password harus lebih dari 8 karakter"
                false
            }
            else -> {
                passTxf.error = null
                true
            }
        }
    }


    private fun loginError(message: String) {
        if (message.contains("email")) {
            binding.textInputEmailRegis.error = "Email tidak terdaftar"
        } else if (message.contains("password")) {
            binding.textInputPasswordLogin.error = "Password salah"
        } else  {
            binding.textInputEmailRegis.error = null
            binding.textInputPasswordLogin.error = null
        }
    }
    private fun isValidEmail(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}