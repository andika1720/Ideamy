package com.example.thefinalproject.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.thefinalproject.databinding.FragmentDetailCourseBinding
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.network.model.course.DataCourseById
import com.example.thefinalproject.network.model.course.DetailResponse
import com.example.thefinalproject.ui.activity.MainActivity
import com.example.thefinalproject.util.Status
import org.koin.android.ext.android.inject

class DetailCourse : Fragment() {

    private var _binding: FragmentDetailCourseBinding? = null
    private val binding = _binding!!
    private val viewMode: ViewModelAll by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailCourseBinding.inflate(inflater, container, false)
       // val fragmentList1 = arrayListOf(ItemTentang(),TesterFragment())
        val bottomNavigationView = (requireActivity() as MainActivity).getBottomNavigationView()
/*
        binding.apply {
            viewPager2Course.adapter = AdapterTentang(fragmentList1, requireActivity().supportFragmentManager, lifecycle)
            TabLayoutMediator(tabLayoutDetailCourse, viewPager2Course) { tab, position ->
                when(position) {
                    0 -> {tab.text = "Tentang"}
                    1 -> {tab.text = "Materi Kelas"}
                }
            }.attach()
        }

 */

        binding.viewPager2Course.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        bottomNavigationView.visibility = View.GONE
                    }

                    1 -> {
                        bottomNavigationView.visibility = View.VISIBLE
                    }

                    else -> {
                        bottomNavigationView.visibility = View.VISIBLE
                    }
                }
            }
        })
        val arg =arguments?.getString("selectedId")

        showDetail(arg.toString())

        return binding.root
    }

    private fun showDetail(id: String) {
        viewMode.getDataById(id).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.d("TESTGETDATABYID", it.data.toString())
                    it.data?.let { data -> showData(data) }
                }

                Status.ERROR -> {
                    Log.d("Error", "Error Occurred!")
                }

                Status.LOADING -> {
                    Log.d("TESTGETDATA", it.data.toString())
                }
            }
        }
    }


    private fun showData(data: DetailResponse){
        val courseData: DataCourseById? = data.data

        binding.tvCategoryCourse.text = courseData?.category
        binding.tvTopicCourse.text = courseData?.title
        binding.tvAuthorCourse.text = courseData?.creator
        binding.tvLevel.text = "${courseData?.level} Level"
        binding.tvWaktucourse.text = "${courseData?.totalDuration} Menit"
        binding.tvModule.text = "${courseData?.totalModule} Modul"

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        val bottomNavigationView = (requireActivity() as MainActivity).getBottomNavigationView()
        bottomNavigationView.visibility = View.VISIBLE
    }

    }

