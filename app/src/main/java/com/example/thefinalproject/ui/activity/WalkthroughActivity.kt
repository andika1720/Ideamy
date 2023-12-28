package com.example.thefinalproject.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.thefinalproject.R
import com.example.thefinalproject.adapter.WalkthroughAdapter
import com.example.thefinalproject.databinding.ActivityWalkthroughBinding
import com.google.android.material.tabs.TabLayout

class WalkthroughActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWalkthroughBinding
    private lateinit var walkthroughAdapter: WalkthroughAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalkthroughBinding.inflate(layoutInflater)
        setContentView(binding.root)

        walkthroughAdapter = WalkthroughAdapter(this)
        binding.vpWalkthrough.adapter = walkthroughAdapter

        // Bind TabLayout with ViewPager
        binding.tabLayout.setupWithViewPager(binding.vpWalkthrough, true)

        // Customize TabLayout
        setupTabLayout()

        binding.nextbtn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
        binding.txtLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.vpWalkthrough.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                // Handle page selection (optional)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun setupTabLayout() {
        val tabLayout = binding.tabLayout
        val tabCount = walkthroughAdapter.count

        for (i in 0 until tabCount) {
            val tab = tabLayout.getTabAt(i)
            val customView = layoutInflater.inflate(R.layout.custom_tab, null)
            tab?.customView = customView
        }

        binding.vpWalkthrough.currentItem = 0

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.customView?.findViewById<View>(R.id.dotIndicator)
                    ?.setBackgroundResource(R.drawable.tab_selected_dot)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView?.findViewById<View>(R.id.dotIndicator)
                    ?.setBackgroundResource(R.drawable.tab_unselected_dot)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Optional: Handle reselection
            }
        })
    }
}