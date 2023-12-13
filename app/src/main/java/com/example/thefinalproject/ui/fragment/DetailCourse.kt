package com.example.thefinalproject.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.thefinalproject.R
import com.example.thefinalproject.adapter.AdapterPageFragment
import com.example.thefinalproject.databinding.FragmentDetailCourseBinding
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.network.model.course.DataCourseById
import com.example.thefinalproject.network.model.course.DetailResponse
import com.example.thefinalproject.ui.activity.MainActivity
import com.example.thefinalproject.ui.fragment.itemPage.detail.DetailcourseTentangFragment
import com.example.thefinalproject.ui.fragment.itemPage.detail.MateriKelas
import com.example.thefinalproject.util.Status
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.ext.android.inject

class DetailCourse : Fragment() {

    private var _binding: FragmentDetailCourseBinding? = null
    private val binding get() =  _binding!!
    private val viewMode: ViewModelAll by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailCourseBinding.inflate(inflater, container, false)

        val fragmentList = arrayListOf(DetailcourseTentangFragment(), MateriKelas())
        val bottomNavigationView = (requireActivity() as MainActivity).getBottomNavigationView()

        binding.apply {
            viewPager2Course.adapter = AdapterPageFragment(fragmentList, requireActivity().supportFragmentManager, lifecycle)
            TabLayoutMediator(tabLayoutDetailCourse, viewPager2Course) { tab, position ->
                when(position) {
                    0 -> {tab.text = "Tentang"}
                    1 -> {tab.text = "Materi Kelas"}
                }
            }.attach()
        }

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

        binding.icArrowBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailCourse_to_homeFragment2)
        }

        val arg =arguments?.getString("selectedId")

        showDetail(arg.toString())


        return binding.root
    }



    private fun showDetail(id: String) {
        viewMode.getDataById(id).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {

                    it.data?.let { data -> showData(data) }
                }

                Status.ERROR -> {
                    Log.d("Error", "Error Occurred!")
                }

                Status.LOADING -> {
                    Log.d("Data", it.data.toString())
                }
            }
        }
    }


    private fun showData(data: DetailResponse){
        val courseData: DataCourseById? = data.data

        binding.tvCategoryCourse.text = courseData?.category
        binding.tvTopicCourse.text = courseData?.title
        binding.tvModule.text = "${courseData?.totalModule} Modul"
        binding.tvAuthorCourse.text = courseData?.creator
        binding.tvLevel.text = "${courseData?.level} Level"
        binding.tvWaktucourse.text = "${courseData?.totalDuration} Menit"

        val bundle = Bundle()
        bundle.putString("description", courseData?.description)
        bundle.putString("telegramLink", courseData?.telegram)

        val tentangFragment = DetailcourseTentangFragment()
        tentangFragment.arguments = bundle

        val fragmentList = arrayListOf(tentangFragment, MateriKelas())
        binding.viewPager2Course.adapter = AdapterPageFragment(fragmentList, requireActivity().supportFragmentManager, lifecycle)

    }
    override fun onDestroyView() {
        super.onDestroyView()
        val bottomNavigationView = (requireActivity() as MainActivity).getBottomNavigationView()
        bottomNavigationView.visibility = View.VISIBLE
    }
 }

