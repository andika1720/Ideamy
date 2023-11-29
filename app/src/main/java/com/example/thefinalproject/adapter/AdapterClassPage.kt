package com.example.thefinalproject.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.thefinalproject.ui.fragment.itemPage.ItemPageAllClass
import com.example.thefinalproject.ui.fragment.itemPage.ItemPageInprogressClass
import com.example.thefinalproject.ui.fragment.itemPage.ItemPageSelesaiClass

class AdapterClassPage(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ItemPageAllClass()
            1 -> ItemPageInprogressClass()
            else -> ItemPageSelesaiClass()

        }
    }
}