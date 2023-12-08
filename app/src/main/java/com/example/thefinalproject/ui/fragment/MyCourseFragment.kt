package com.example.thefinalproject.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.thefinalproject.R
import com.example.thefinalproject.adapter.AdapterCoursePage
import com.example.thefinalproject.databinding.FragmentMyCourseBinding
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.network.model.DataCategory
import com.example.thefinalproject.network.model.DataCourse
import com.example.thefinalproject.network.model.DetailResponse
import com.example.thefinalproject.network.model.ListResponse
import com.example.thefinalproject.util.Status
import com.example.thefinalproject.util.Utils
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.ext.android.inject

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

        binding.tvFilterKursus.setOnClickListener {
            showBottomSheetDialog()
        }
    }


    private fun showBottomSheetDialog() {
        val bsDialog = BottomSheetDialog(requireContext())
        val bottomSheetView = layoutInflater.inflate(R.layout.botsheet_filtering_class, null)


        bsDialog.setContentView(bottomSheetView)
        bsDialog.show()



    }

}