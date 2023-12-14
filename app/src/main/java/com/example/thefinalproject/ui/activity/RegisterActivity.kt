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


//    private fun validatePass() {
//        binding.etPasswordRegis.doOnTextChanged { text, _, _, _ ->
//            if (text.isNullOrBlank()) {
//                binding.textFieldPasswordRegis.error = "Password cannot be empty!"
//            } else if (!text.matches(".*[A-Z].*".toRegex())) {
//                binding.textFieldPasswordRegis.error = "Must Contain 1 Upper-case Character!"
//            } else if (!text.matches(".*[a-z].*".toRegex())) {
//                binding.textFieldPasswordRegis.error = "Must Contain 1 Lower-case Character!"
//            } else if (!text.matches(".*[@#\$%^&+=_].*".toRegex())) {
//                binding.textFieldPasswordRegis.error = "Must Contain 1 Special Character! (@#\$%^&+=_)"
//            } else if (text.length <8 ) {
//                binding.textFieldPasswordRegis.error = "Minimum 8 characters!"
//            } else {
//                binding.textFieldPasswordRegis.error = null
//                binding.textFieldPasswordRegis.errorIconDrawable = binding.etPasswordRegis.context.getDrawable(R.drawable.ic_check)
//            }
//        }
//    }
//
//    private fun validateNoHp() {
//        binding.etNohpRegis.doOnTextChanged { text, _, _, _ ->
//            if (text!!.length > 13) {
//                binding.textInputNohpRegis.error = "Max 15!"
//            } else if (text.length <= 13) {
//                binding.textInputNohpRegis.error = null
//            }
//        }
//    }
//
//    private fun validateEmail() {
//        binding.etEmailRegis.doOnTextChanged { text, _, _, _ ->
//            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
//
//            if (text.isNullOrBlank()) {
//                binding.textInputEmailRegis.error = "Email cannot be empty!"
//            } else if (!text.matches(emailPattern.toRegex())) {
//                binding.textInputEmailRegis.error = "Invalid email format!"
//            } else if (text.length > 50) {
//                binding.textInputEmailRegis.error = "Max 50 characters!"
//            } else {
//                binding.textInputEmailRegis.error = null
//            }


//    private fun emailFocus(){
//        binding.etEmailRegis.setOnFocusChangeListener { _, focused ->
//            if (!focused){
//                binding.textInputEmailRegis.helperText = validEmail()
//            }
//        }
//    }
//
//    private fun validEmail(): String? {
//        val emailText = binding.etEmailRegis.text.toString()
//        if (Patternsdika.EMAIL_ADDRESS.matcher(emailText).matches())
//        {
//            return "Invalid Email Address"
//        }
//            return null
//    }

