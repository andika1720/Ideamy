package com.example.thefinalproject.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.example.thefinalproject.R
import com.example.thefinalproject.adapter.AdapterPageFragment
import com.example.thefinalproject.adapter.CourseAdapter
import com.example.thefinalproject.databinding.FragmentMyCourseBinding
import com.example.thefinalproject.network.model.course.DataCategory
import com.example.thefinalproject.ui.fragment.botsheet.BotSheetLogin
import com.example.thefinalproject.ui.fragment.botsheet.BotsheetSelangkah

import com.example.thefinalproject.ui.fragment.itemPage.course.KelasFree
import com.example.thefinalproject.ui.fragment.itemPage.course.KelasPremium
import com.example.thefinalproject.ui.fragment.itemPage.course.SemuaKelasCourseFragment
import com.example.thefinalproject.util.SharePref
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator

class MyCourseFragment : Fragment(), CourseAdapter.CourseItemClickListener {
    private lateinit var sharePref: SharePref
    private var _binding: FragmentMyCourseBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragmen
        _binding = FragmentMyCourseBinding.inflate(layoutInflater,container,false)
        sharePref = SharePref
        val fragmentList = arrayListOf( SemuaKelasCourseFragment(),
            KelasPremium(),
            KelasFree()
        )
        val titleFragment = arrayListOf("Semua Kelas", "Premium", "Free")
        val adapter = AdapterPageFragment(fragmentList,this, childFragmentManager, lifecycle)
        binding.apply {
            viewpageCourse.adapter = adapter
            TabLayoutMediator(tabLayoutKursus, viewpageCourse) { tab, position ->
                tab.text = titleFragment[position]
            }.attach()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onCourseItemClick(data: DataCategory) {
        val bundle = Bundle().apply {
            putString("selectedId", data.id)
        }
        val isLogin = SharePref.getPref(SharePref.Enum.PREF_NAME.value)
        if (isLogin != null) {
            if (data.type == "premium") {
                val bottomSheetSelangkah = BotsheetSelangkah()
                bottomSheetSelangkah.setCourseId(bundle)
                bottomSheetSelangkah.show(childFragmentManager, bottomSheetSelangkah.tag)
            } else if (data.type == "free") {
                findNavController().navigate(R.id.action_homeFragment2_to_detailCourse, bundle)
            }
        } else {
            val botsheetLogin = BotSheetLogin()
            botsheetLogin.show(childFragmentManager, botsheetLogin.tag)
        }
    }




    @SuppressLint("InflateParams")
    private fun bonSheetSelangkah() {
        try {
            val dialog = BottomSheetDialog(requireContext())
            val view = layoutInflater.inflate(R.layout.botsheet_belicourse_detailcourse, null)
            val btnSheetSelangkah = view.findViewById<Button>(R.id.btn_beliSekarang)
            val btnClose = view.findViewById<ImageView>(R.id.ic_close)
            btnSheetSelangkah.setOnClickListener {
                dialog.dismiss()
            }
            btnClose.setOnClickListener {
                dialog.dismiss()
            }
            dialog.setCanceledOnTouchOutside(false)
            dialog.setContentView(view)
            dialog.show()
        }catch (e: Exception) {
            Log.e("showbotPayment", "ErrorBotsheet", e)
        }
    }
}


