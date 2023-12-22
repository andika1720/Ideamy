package com.example.thefinalproject.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.thefinalproject.R
import com.example.thefinalproject.mvvm.viewmmodel.AuthViewModel
import com.example.thefinalproject.util.SharePref
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity() {
    private lateinit var sharePref: SharePref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        sharePref = SharePref
        sharePref.initial(this)
        Handler().postDelayed({
            if (sharePref.isLoggedIn()) {
                startActivity(Intent(this, MainActivity::class.java))
            }else{
                startActivity(Intent(this, WalkthroughActivity::class.java))
            }
            finish()
        }, 2000)

    }
}