package com.example.thefinalproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.thefinalproject.R
import com.example.thefinalproject.databinding.ItemWalkthroughBinding

class WalkthroughAdapter(private val context: Context) : PagerAdapter() {

    private val titles = arrayOf(
        "Belajar dari Pengalaman Terbaik!",
        "Belajar dari Praktisi Terbaik!",
        "Belajar darimana saja!"
    )

    private val images = arrayOf(
        R.drawable.project_planning,
        R.drawable.analytics,
        R.drawable.share_location
    )

    override fun getCount(): Int {
        return titles.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = ItemWalkthroughBinding.inflate(LayoutInflater.from(context), container, false)
        binding.ivImage.setImageResource(images[position])
        binding.tvTitle.text = titles[position]
        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
