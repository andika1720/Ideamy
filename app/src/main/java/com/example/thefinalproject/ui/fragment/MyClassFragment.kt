package com.example.thefinalproject.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.thefinalproject.R
import com.example.thefinalproject.adapter.AdapterClassPage
import com.example.thefinalproject.databinding.FragmentMyClassBinding
import com.example.thefinalproject.databinding.FragmentMyCourseBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MyClassFragment : Fragment() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: AdapterClassPage
    private lateinit var binding: FragmentMyClassBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyClassBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabLayout = binding.tabLayoutClass
        viewPager2 = binding.viewpageClass
        adapter = AdapterClassPage(this)
        viewPager2.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = "Semua Kelas"
                1 -> tab.text = "In Progress"
                2 -> tab.text = "Selesai"
            }
        }.attach()
    }
}