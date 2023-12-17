package com.example.thefinalproject.ui.activity

import SharePref
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.thefinalproject.R
import com.example.thefinalproject.databinding.ActivityMainBinding
import com.example.thefinalproject.ui.fragment.botsheet.BotSheetLogin

import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharePref.initial(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener {_,destination,_ ->
            val isLogin = SharePref.getPref(SharePref.Enum.PREF_NAME.value)
            if (isLogin == null) {
                when (destination.id) {
                    R.id.notifikasiFragment2, R.id.settingFragment2, R.id.myClassFragment2 -> {
                        val bottomSheetFragmentMustLogin = BotSheetLogin()
                        bottomSheetFragmentMustLogin.show(supportFragmentManager, bottomSheetFragmentMustLogin.tag)
                        navController.navigate(R.id.homeFragment2)
                    }
                }
            }
        }

    }

    fun getBottomNavigationView(): BottomNavigationView {
        return binding.bottomNavigationView
    }
}