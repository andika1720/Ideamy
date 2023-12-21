package com.example.thefinalproject.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.thefinalproject.R
import com.example.thefinalproject.databinding.ActivityRegisterBinding
import com.example.thefinalproject.mvvm.repository.Repository
import com.example.thefinalproject.mvvm.viewmmodel.AuthViewModel

import com.example.thefinalproject.network.model.user.register.RegisterRequest
import com.example.thefinalproject.ui.fragment.OtpCode
import com.example.thefinalproject.util.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewmodel: AuthViewModel by inject()
    private lateinit var hiddenViewContent: ConstraintLayout

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.masukDisiniRegis.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnDaftarRegis.setOnClickListener {
            val namaText = binding.etNamaRegis.text.toString()
            val noHpText = binding.etNohpRegis.text.toString()
            val emailText = binding.etEmailRegis.text.toString()
            val passwordText = binding.etPasswordRegis.text.toString()


            // Fokus pada bidang nama jika belum terisi
            if (namaText.isBlank()) {
                binding.etNamaRegis.error = "Nama harus diisi"
                binding.etNamaRegis.requestFocus()
                return@setOnClickListener
            }
            // Memeriksa panjang nama
            if (namaText.length < 6) {
                binding.etNamaRegis.error = "Name must be at least 6 characters"
                binding.etNamaRegis.requestFocus()
                return@setOnClickListener
            } else if (emailText.isBlank()) {
                binding.etEmailRegis.error = "Email harus diisi"
                binding.etEmailRegis.requestFocus()
                return@setOnClickListener
            } else if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
                binding.etEmailRegis.error = "Email tidak valid"
                binding.etEmailRegis.requestFocus()
                return@setOnClickListener
            } else if (noHpText.isBlank()) {
                binding.etNohpRegis.error = "No HP harus diisi"
                binding.etNohpRegis.requestFocus()
                return@setOnClickListener
            } else if (noHpText.length < 11 || noHpText.length > 11) {
                binding.etNohpRegis.error = "Masukan 12 angka"
                binding.etNohpRegis.requestFocus()
                return@setOnClickListener
            } else if (passwordText.isBlank()) {
                binding.etPasswordRegis.error = "Password harus diisi"
                binding.etPasswordRegis.requestFocus()
                return@setOnClickListener
            } else if (passwordText.length < 8 || passwordText.length > 12) {
                binding.etPasswordRegis.error = "Password Harus Terdiri dari 8-12 Karakter"
                binding.etPasswordRegis.requestFocus()
                return@setOnClickListener
            } else {
                val hpreal = "0$noHpText"
                Log.e("Isi telephon", hpreal)
                Regis(namaText, emailText, hpreal, passwordText)
            }
        }
    }

    private fun Regis(nama: String, email: String, nohp: String, password: String) {
        val regisReq = RegisterRequest(nama, email, nohp, password)

        viewmodel.regisUser(regisReq).observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    val fragment = OtpCode()
                    val bundle = Bundle()
                    bundle.putParcelable("dataRegis", regisReq)
                    fragment.arguments = bundle

                    hiddenViewContent = findViewById(R.id.regisContent)

                    if (hiddenViewContent.visibility == View.VISIBLE) {
                        hiddenViewContent.visibility = View.GONE
                    } else {
                        hiddenViewContent.visibility = View.VISIBLE
                    }

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container2, fragment)
                        .commit()

                }

                Status.ERROR -> {
                    val errorMessage = it.message ?: "Error Occurred!"
                    Log.d("errorRegis", errorMessage)

                    registerError(errorMessage)


                }

                Status.LOADING -> {
                    Log.d("load", "Loading")
                }
            }
        }
    }

    private fun registerError(message: String) {
        when {
            message.contains("HTTP 500") -> {
                Toast.makeText(this, "Email sudah digunakan", Toast.LENGTH_SHORT).show()
            }
            message.contains("HTTP 400") -> {
                Toast.makeText(this, "Email sudah digunakan", Toast.LENGTH_SHORT).show()
            }
            // Tambahkan penanganan error lain sesuai kebutuhan
            else -> {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

}


