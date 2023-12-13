package com.example.thefinalproject.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class AdapterPageFragment(
    var fragmentList: ArrayList<Fragment>,
    private val courseItemClickListener: CourseAdapter.CourseItemClickListener,
    fm: FragmentManager,
    lifecycle: Lifecycle
): FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = fragmentList[position]
        if (fragment is CourseAdapter.CourseItemClickListenerProvider) {
            (fragment as CourseAdapter.CourseItemClickListenerProvider).setItemClickListener(courseItemClickListener)
        }
        return fragment
    }
}