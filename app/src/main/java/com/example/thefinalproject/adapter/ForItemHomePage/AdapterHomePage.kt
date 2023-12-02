package com.example.thefinalproject.adapter.ForItemHomePage

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.thefinalproject.ui.fragment.HomeFragment
import com.example.thefinalproject.ui.fragment.itemPage.HomePage.ItemForProductm
import com.example.thefinalproject.ui.fragment.itemPage.HomePage.ItemForUiux
import com.example.thefinalproject.ui.fragment.itemPage.HomePage.ItemForWebDev
import com.example.thefinalproject.ui.fragment.itemPage.HomePage.ItemSemuaKelas
import com.example.thefinalproject.ui.fragment.itemPage.ItemAllFragment
import com.example.thefinalproject.ui.fragment.itemPage.ItemKelasGeratis
import com.example.thefinalproject.ui.fragment.itemPage.ItemKelaspremiumFragment

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