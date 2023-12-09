package com.example.thefinalproject.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.thefinalproject.adapter.AdapterCategory
import com.example.thefinalproject.adapter.AdapterClassPage
import com.example.thefinalproject.databinding.FragmentMyClassBinding
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.network.model.course.CategoryResponse
import com.example.thefinalproject.util.Status
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.ext.android.inject

class MyClassFragment : Fragment() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: AdapterClassPage
    private lateinit var binding: FragmentMyClassBinding
    private val viewMode: ViewModelAll by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMyClassBinding.inflate(layoutInflater, container, false)

        fetchCategory(null)
        /*
        binding.tabLayoutClass.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

        })

         */

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


    private fun fetchCategory(category: String?) {
        viewMode.getAllCategory(category).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    showCategory(it.data)

                    binding.progressbarCategory.isVisible = false

                }

                Status.ERROR -> {
                    binding.progressbarCategory.isVisible = false
                    Log.e("ERROR", it.message.toString())
                }

                Status.LOADING -> {
                    binding.progressbarCategory.isVisible = true
                }
            }
        }
    }

    private fun showCategory(data: CategoryResponse?) {
        val uniqueCategories = data?.data?.distinctBy { it.category }
        val adapter = AdapterCategory(null)
        adapter.sendCategory(uniqueCategories ?: emptyList())
        binding.rvCategory.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.rvCategory.adapter = adapter
    }
}