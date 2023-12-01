package com.example.thefinalproject.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.thefinalproject.ui.fragment.itemPage.ItemAllFragment
import com.example.thefinalproject.ui.fragment.itemPage.ItemKelaspremiumFragment
import com.example.thefinalproject.ui.fragment.itemPage.ItemKelasGeratis

class AdapterCoursePage(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ItemAllFragment()
            1 -> ItemKelaspremiumFragment()
            else -> ItemKelasGeratis()
        }
    }

    fun getCourseType(position: Int): String {
        return when (position) {
            0 -> "all"
            1 -> "premium"
            else -> "gratis"
        }
    }


}
