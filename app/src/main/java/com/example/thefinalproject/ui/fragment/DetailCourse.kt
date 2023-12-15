package com.example.thefinalproject.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.thefinalproject.R
import com.example.thefinalproject.adapter.AdapterPageForDetail

import com.example.thefinalproject.databinding.FragmentDetailCourseBinding
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.network.model.course.ChapterById
import com.example.thefinalproject.network.model.course.DataCourseById
import com.example.thefinalproject.network.model.course.DetailResponse
import com.example.thefinalproject.network.model.course.ModuleById
import com.example.thefinalproject.ui.activity.MainActivity
import com.example.thefinalproject.ui.fragment.itemPage.detail.DetailcourseTentangFragment
import com.example.thefinalproject.ui.fragment.itemPage.detail.MateriKelas

import com.example.thefinalproject.util.Status
import com.google.android.material.tabs.TabLayoutMediator
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
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

        val savedToken = SharePref.getPref(SharePref.Enum.PREF_NAME.value)
        val fragmentList = arrayListOf(DetailcourseTentangFragment(), MateriKelas())
        val bottomNavigationView = (requireActivity() as MainActivity).getBottomNavigationView()

        binding.apply {
            viewPager2Course.adapter = AdapterPageForDetail(fragmentList, requireActivity().supportFragmentManager, lifecycle)
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

        showDetail(savedToken.toString(),arg.toString())


        return binding.root
    }



    private fun showDetail(token:String?,id: String) {
        viewMode.getDataById("Bearer $token",id).observe(viewLifecycleOwner) {
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


    @SuppressLint("SetTextI18n")
    private fun showData(data: DetailResponse){
        val courseData: DataCourseById? = data.data

        val youTubePlayerView: YouTubePlayerView = binding.playerView
        lifecycle.addObserver(youTubePlayerView)
        youTubePlayerView.addYouTubePlayerListener(object: AbstractYouTubePlayerListener(){
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)

                val firstChapter: ChapterById? = courseData?.chapters?.getOrNull(0)
                val firstModule: ModuleById? = firstChapter?.modules?.getOrNull(0)

                val videoUrl = youtubeVidStatic(firstModule?.video ?: "")

                Log.d("VIDEOID", videoUrl.toString())
                youTubePlayer.loadVideo(videoUrl.toString(), 0F)
            }
        })
        binding.tvCategoryCourse.text = courseData?.category
        binding.tvTopicCourse.text = courseData?.title
        binding.tvModule.text = "${courseData?.totalModule} Modul"
        binding.tvAuthorCourse.text = courseData?.creator
        binding.tvLevel.text = "${courseData?.level} Level"
        binding.tvWaktucourse.text = "${courseData?.totalDuration} Menit"


        val bundle = Bundle()
        bundle.putString("selectedId", courseData?.id)
        //bundle.putString("audience", courseData?.audience.toString())
        //bundle.putString("telegramLink", courseData?.telegram)

        val tentangFragment = DetailcourseTentangFragment()
        tentangFragment.arguments = bundle

        val fragmentList = arrayListOf(tentangFragment, MateriKelas())
        binding.viewPager2Course.adapter = AdapterPageForDetail(fragmentList, requireActivity().supportFragmentManager, lifecycle)

    }


    private fun youtubeVidStatic(youtubeUrl: String): String? {
        var videoId: String? = null
        val pattern = Regex("^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$", RegexOption.IGNORE_CASE)
        val matcher = pattern.find(youtubeUrl)
        if (matcher != null && matcher.groupValues.size > 1) {
            videoId = matcher.groupValues[1]
        }
        return videoId
    }


    override fun onDestroyView() {
        super.onDestroyView()
        val bottomNavigationView = (requireActivity() as MainActivity).getBottomNavigationView()
        bottomNavigationView.visibility = View.VISIBLE
    }
 }

