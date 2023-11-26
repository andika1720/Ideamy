package com.example.thefinalproject.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.thefinalproject.adapter.AdapterCoursePage
import com.example.thefinalproject.databinding.FragmentMyCourseBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MyCourseFragment : Fragment() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var binding: FragmentMyCourseBinding
    private lateinit var adapter: AdapterCoursePage
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragmen
        binding = FragmentMyCourseBinding.inflate(layoutInflater,container,false)
        return binding.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabLayout = binding.tabLayoutKursus
        viewPager2 = binding.viewpageKursus

        adapter = AdapterCoursePage(this)
        viewPager2.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            // Mengatur judul tab sesuai dengan posisi
            when (position) {
                0 -> tab.text = "Semua Kelas"
                1 -> tab.text = "Kelas Premium"
                2 -> tab.text = "Kelas Gratis "
            }
        }.attach()
    }
}