package com.example.thefinalproject.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.example.thefinalproject.R
import com.example.thefinalproject.adapter.WalkthroughAdapter
import com.example.thefinalproject.databinding.ActivityWalkthroughBinding

class WalkthroughActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWalkthroughBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalkthroughBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val walkthroughAdapter = WalkthroughAdapter(this)
        binding.vpWalkthrough.adapter = walkthroughAdapter

        binding.nextbtn.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
            finish()
        }
        binding.txtLogin.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
        binding.vpWalkthrough.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }
}