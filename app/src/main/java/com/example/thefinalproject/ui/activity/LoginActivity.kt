package com.example.thefinalproject.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.thefinalproject.R
import com.example.thefinalproject.databinding.ActivityLoginBinding
import com.example.thefinalproject.network.model.user.login.LoginRequest
import com.example.thefinalproject.util.Status
import com.example.thefinalproject.mvvm.viewmmodel.AuthViewModel
import com.example.thefinalproject.ui.fragment.forgotpass.InputEmail
import com.example.thefinalproject.util.SharePref
import com.example.thefinalproject.util.Utils
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewmodel: AuthViewModel by inject()
    private lateinit var hiddenViewContent: ConstraintLayout
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
        binding.lupaPwLogin.setOnClickListener {
            val fragment = InputEmail()
            val bundle = Bundle()
            fragment.arguments = bundle
            hiddenViewContent = findViewById(R.id.login_content)

            if (hiddenViewContent.visibility == View.VISIBLE) {
                hiddenViewContent.visibility = View.GONE
            } else {
                hiddenViewContent.visibility = View.VISIBLE
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.container3, fragment)
                .commit()


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
                        Utils.toastMessage(this, "Login berhasil! Mengalihkan ke halaman Beranda...")
                        navigateToMainActivity()
                        Log.d("LoginBerhasil", "Token= $token")
                    }
                    Status.ERROR -> {
                        val errorMessage = it.message ?: "Error Occurred!"
                        Log.d("errorLogin", errorMessage)
                        Utils.toastMessage(this, "Login gagal silahkan coba kembali...")
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
                emailTxf.error = "Email Harus diisi"
                false
            }
            !isValidEmail(email) -> {
                emailTxf.error = "Format email tidak valid"
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
                passTxf.error = "Password harus diisi"
                false
            }
            edPass.text.toString().trim().length < 8 -> {
                passTxf.error = "Password harus terdiri dari 8-12 karakter"
                false
            }
            else -> {
                passTxf.error = null
                true
            }
        }
    }


    private fun isValidEmail(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }
}
