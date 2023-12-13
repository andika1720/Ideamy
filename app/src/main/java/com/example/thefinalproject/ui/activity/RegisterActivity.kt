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
    private val authViewModel:AuthViewModel by lazy {
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
        /*
        binding.btnDaftarRegis.setOnClickListener {

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        binding.etNamaRegis.doOnTextChanged {text, _, _, _ ->
            if (text!!.length <3){
                binding.textInputNamaRegis.error = "Minimum 5Characters"
            } else if (text.isEmpty()) {
                Toast.makeText(this, "Nama belum terisi", Toast.LENGTH_SHORT).show()
                binding.textInputNamaRegis.requestFocus()
            }
            return@doOnTextChanged
        }


        binding.etNohpRegis.doOnTextChanged { text, _, _, _ ->
            if (text!!.isNullOrEmpty()) {
                binding.textInputNohpRegis.requestFocus()
                Toast.makeText(this, "No Handphone belum terisi", Toast.LENGTH_SHORT).show()
            } else if (text.length > 13) {
                binding.textInputNohpRegis.error = "Max 15!"

            }else if (text.length <= 13){
            binding.textInputNohpRegis.error = null

            }
            return@doOnTextChanged
        }


        binding.etEmailRegis.doOnTextChanged { text, _, _, _ ->
            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

            if (text.isNullOrEmpty()) {
                binding.textInputEmailRegis.error = "Email cannot be empty!"
                Toast.makeText(this, "Email belum terisi", Toast.LENGTH_SHORT).show()
                binding.textInputEmailRegis.requestFocus()
            } else if (!text.matches(emailPattern.toRegex())) {
                binding.textInputEmailRegis.error = "Invalid email format!"
            } else if (text.length > 50) {
                binding.textInputEmailRegis.error = "Max 50 characters!"
            } else {
                binding.textInputEmailRegis.error = null
            }
            return@doOnTextChanged
        }

        binding.etPasswordRegis.doOnTextChanged { text, _, _, _ ->
            if (text.isNullOrEmpty()) {
                binding.textFieldPasswordRegis.error = "Password cannot be empty!"
                Toast.makeText(this, "Password belum terisi", Toast.LENGTH_SHORT).show()

                binding.textFieldPasswordRegis.requestFocus()
            } else if (!text.matches(".*[A-Z].*".toRegex())) {
                binding.textFieldPasswordRegis.error =
                    "Must Contain 1 Upper-case Character!"
            } else if (!text.matches(".*[a-z].*".toRegex())) {
                binding.textFieldPasswordRegis.error =
                    "Must Contain 1 Lower-case Character!"
            } else if (!text.matches(".*[@#\$%^&+=_].*".toRegex())) {
                binding.textFieldPasswordRegis.error =
                    "Must Contain 1 Special Character! (@#\$%^&+=_)"
            } else if (text.length < 8) {
                binding.textFieldPasswordRegis.error = "Minimum 8 characters!"
            } else {
                binding.textFieldPasswordRegis.error = null
                binding.textFieldPasswordRegis.errorIconDrawable =
                    binding.etPasswordRegis.context.getDrawable(R.drawable.ic_check)
            }
            return@doOnTextChanged

        }
            return@setOnClickListener

 */
        binding.btnDaftarRegis.setOnClickListener {
            val namaText = binding.etNamaRegis.text.toString()
            val noHpText = binding.etNohpRegis.text.toString()
            val emailText = binding.etEmailRegis.text.toString()
            val passwordText = binding.etPasswordRegis.text.toString()
            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)


            if (namaText.isBlank() || noHpText.isBlank() || emailText.isBlank() || passwordText.isBlank()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                // Fokus pada bidang nama jika belum terisi
                if (namaText.isBlank()) {
                    binding.etNamaRegis.requestFocus()
                }
            } else {
                // Memeriksa panjang nama
                if (namaText.length <= 6) {
                    Toast.makeText(this, "Name must be at least 6 characters", Toast.LENGTH_SHORT)
                        .show()
                    binding.etNamaRegis.requestFocus()
                    return@setOnClickListener
                } else if (binding.textInputNohpRegis.error != null) {
                    Toast.makeText(
                        this,
                        "Please correct the phone number field",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                } else if (noHpText.length < 11 || noHpText.length > 11) {
                    binding.textInputNohpRegis.error = "masukan 12 Number"

                    return@setOnClickListener
                } else if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
                    binding.etEmailRegis.error = "Email Tidak Valid"
                    binding.etEmailRegis.requestFocus()
                    return@setOnClickListener
                } else if (binding.textFieldPasswordRegis.error != null) {
                    Toast.makeText(this, "Please correct the password field", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                } else {
                    val hpreal = "0$noHpText"
                    Log.e("Isi telephon",hpreal)

                    // Call the registration API
                    CoroutineScope(Dispatchers.Main).launch {
                        try {
                            authViewModel.regisUser(
                                RegisterRequest(
                                    namaText,
                                    hpreal,
                                    emailText,
                                    passwordText
                                )
                            )
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
//                        val fragmentOTP = OtpCode()
//                        supportFragmentManager.beginTransaction()
//                            .replace(R.id.container2, fragmentOTP)
//                            .addToBackStack(null)
//                            .commit()

                        /*
                    when {
                        namaText.isEmpty() -> {
                         Toast.makeText(this, "Nama belum terisi", Toast.LENGTH_SHORT).show()
                          binding.etNamaRegis.requestFocus()
                            return@setOnClickListener
                        }

                        emailText.isEmpty() -> {
                            Toast.makeText(this, "Email Belum terisi", Toast.LENGTH_SHORT).show()
                            binding.etEmailRegis.requestFocus()
                            return@setOnClickListener
                        }

                        noHpText.isEmpty() -> {
                            Toast.makeText(this, "No Handphone belum terisi", Toast.LENGTH_SHORT).show()
                            binding.etNohpRegis.requestFocus()
                            return@setOnClickListener
                        }

                        passwordText.isEmpty() -> {
                            Toast.makeText(this, "Password Belum terisi", Toast.LENGTH_SHORT).show()
                            binding.etPasswordRegis.requestFocus()
                            return@setOnClickListener
                        }
                    }

                 */

                }
            }
        }
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

