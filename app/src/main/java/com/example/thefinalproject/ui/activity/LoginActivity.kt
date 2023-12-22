package com.example.thefinalproject.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.thefinalproject.databinding.ActivityLoginBinding
import com.example.thefinalproject.network.model.user.login.LoginRequest
import com.example.thefinalproject.util.Status
import com.example.thefinalproject.mvvm.viewmmodel.AuthViewModel
import com.example.thefinalproject.util.SharePref
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewmodel: AuthViewModel by inject()
    private lateinit var sharePref: SharePref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharePref = SharePref
        sharePref.initial(this)

        if (sharePref.isLoggedIn()) {
            // User is already logged in, navigate to the main activity
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.daftardisiniLogin.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))

        }

        binding.masukWithoutLogin.setOnClickListener {
           navigateToMainActivity()
        }
        binding.btnMasukLogin.setOnClickListener {
            val emailText = binding.etEmailLogin.text.toString()
            val passwordText = binding.etPasswordRegis.text.toString()
            login(emailText, passwordText)
        }
    }

    private fun login(email: String, password: String) {
        if (emailValid(binding.etEmailLogin, binding.textInputEmailRegis) &&
            passwordValid(binding.etPasswordRegis, binding.textInputPasswordLogin)
        ) {
            val loginReq = LoginRequest(email, password)
            viewmodel.loginUser(loginReq).observe(this) {
                when (it.status) {
                    Status.SUCCESS -> {
                        val token = it.data?.data?.token
                        sharePref.setPref(SharePref.Enum.PREF_NAME.value, token)
                        sharePref.setLoginStatus(true)
                        navigateToMainActivity()
                        Log.d("LoginBerhasil", "Token= $token")
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

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun emailValid(edEmail: EditText, emailTxf: TextInputLayout): Boolean {
        val email = edEmail.text.toString().trim()

        return when {
            email.isEmpty() -> {
                emailTxf.error = "Please fill in all fields"
                false
            }
            !isValidEmail(email) -> {
                emailTxf.error = "Email must be unique and valid"
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
                passTxf.error = "Password cannot be empty"
                false
            }
            edPass.text.toString().trim().length < 8 -> {
                passTxf.error = "Password must be at least 8 characters long"
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
            binding.textInputEmailRegis.error = "Email not registered"
        } else if (message.contains("password")) {
            binding.textInputPasswordLogin.error = "Incorrect password"
        } else if(message.contains("HTTP 404")){
            Toast.makeText(this, "Email or password is invalid", Toast.LENGTH_SHORT).show()
        } else {
            binding.textInputEmailRegis.error = null
            binding.textInputPasswordLogin.error = null
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }
}
