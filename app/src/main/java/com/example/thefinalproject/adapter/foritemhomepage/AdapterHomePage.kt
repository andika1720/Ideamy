package com.example.thefinalproject.adapter.foritemhomepage

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.thefinalproject.ui.fragment.itemPage.homepage.ItemForProductm
import com.example.thefinalproject.ui.fragment.itemPage.homepage.ItemForUiux
import com.example.thefinalproject.ui.fragment.itemPage.homepage.ItemForWebDev
import com.example.thefinalproject.ui.fragment.itemPage.homepage.ItemSemuaKelas

class AdapterHomePage(fragment: Fragment) : FragmentStateAdapter(fragment)  {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ItemSemuaKelas()
            1 -> ItemForProductm()
            2 -> ItemForWebDev()
            else -> ItemForUiux()

        }
    }
}