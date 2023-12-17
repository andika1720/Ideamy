package com.example.thefinalproject.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.thefinalproject.R
import com.example.thefinalproject.databinding.ActivityRegisterBinding
import com.example.thefinalproject.mvvm.repository.Repository
import com.example.thefinalproject.mvvm.viewmmodel.AuthViewModel
import com.example.thefinalproject.network.api.ApiClient
import com.example.thefinalproject.network.model.user.register.RegisterRequest
import com.example.thefinalproject.ui.fragment.OtpCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val authViewModel: AuthViewModel by lazy {
        AuthViewModel(Repository(ApiClient.instance))
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        emailFocus()
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
            } else if (emailText.isBlank()){
                binding.etEmailRegis.error = "Email harus diisi"
                binding.etEmailRegis.requestFocus()
                return@setOnClickListener
            } else if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
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
            } else if (passwordText.length < 10 || passwordText.length > 10) {
                binding.etPasswordRegis.error = "Password harus 10 karakter"
                binding.etPasswordRegis.requestFocus()
                return@setOnClickListener
                } else {
                    val hpreal = "0$noHpText"
                    Log.e("Isi telephon", hpreal)
                    val dataRegis = RegisterRequest(namaText,emailText,hpreal,passwordText)

  //                   Call the registration API
                    CoroutineScope(Dispatchers.Main).launch {
                        try {
                            authViewModel.regisUser(dataRegis)
                            Toast.makeText(
                                this@RegisterActivity,
                                "Registration successful",
                                Toast.LENGTH_SHORT
                            ).show()
                            // Redirect to login screen or perform any other action
                        } catch (e: Exception) {
                            Toast.makeText(
                                this@RegisterActivity,
                                "Registration failed: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    val fragment = OtpCode()
                    val bundle = Bundle()

                    bundle.putParcelable("dataRegis",dataRegis)
                    fragment.arguments = bundle
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container2, fragment)
                        .commit()


                }
            }
 //       }
    }
}


