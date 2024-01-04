package com.example.thefinalproject.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.thefinalproject.R
import com.example.thefinalproject.util.SharePref

@Suppress("DEPRECATION")
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var sharePref: SharePref
    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window.statusBarColor = Color.TRANSPARENT
        }
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