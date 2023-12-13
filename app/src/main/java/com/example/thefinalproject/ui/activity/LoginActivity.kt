package com.example.thefinalproject.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.thefinalproject.R
import com.example.thefinalproject.databinding.ActivityLoginBinding

private lateinit var binding: ActivityLoginBinding
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

            if (emailText.isBlank() || passwordText.isBlank()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                // Fokus pada bidang nama jika belum terisi
                if (emailText.isBlank()) {
                    binding.etEmailLogin.requestFocus()
                }
            }else if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
                    binding.etEmailLogin.error = "Email Tidak Valid"
                    binding.etEmailLogin.requestFocus()
                    return@setOnClickListener
            } else{
                startActivity(Intent(this,MainActivity::class.java))
            }
        }



    }
}