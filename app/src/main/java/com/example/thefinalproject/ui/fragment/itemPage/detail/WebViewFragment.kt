package com.example.thefinalproject.ui.fragment.itemPage.detail

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.fragment.findNavController
import com.example.thefinalproject.R
import com.example.thefinalproject.databinding.FragmentWebViewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo

import java.util.regex.Pattern

class WebViewFragment : Fragment() {
    private var _binding: FragmentWebViewBinding? = null
    private val binding get() = _binding!!
    private var isFullScreen = false
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed()  {
            if (isFullScreen) {
                youTubePlayer.toggleFullscreen()
            } else {

            }

        }
    }
    private lateinit var youTubePlayer: YouTubePlayer


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentWebViewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val urlIntro = arguments?.getString("youtube")

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        onBackPressedCallback.isEnabled = true
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)

        val youtubePlayerView = binding.youtubePlayerView
        val fullScreenContainer = binding.fullScreenContainer

        lifecycle.addObserver(youtubePlayerView)

        youtubePlayerView.addFullscreenListener(object : FullscreenListener {
            override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {
                isFullScreen = true
                fullScreenContainer.visibility = View.VISIBLE
                fullScreenContainer.addView(fullscreenView)

                // Full Screen remove status bar and navigation bar
                WindowInsetsControllerCompat(
                    requireActivity().window!!,
                    binding.constraint
                ).apply {
                    hide(WindowInsetsCompat.Type.systemBars())
                    systemBarsBehavior =
                        WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                }

                if (requireActivity().requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    requireActivity().requestedOrientation =
                        ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
                }
            }

            @SuppressLint("SourceLockedOrientationActivity")
            override fun onExitFullscreen() {
                isFullScreen = false
                fullScreenContainer.visibility = View.GONE
                fullScreenContainer.removeAllViews()

                // status bar and navigation bar
                WindowInsetsControllerCompat(
                    requireActivity().window!!,
                    binding.constraint
                ).apply {
                    show(WindowInsetsCompat.Type.systemBars())
                }
                if (requireActivity().requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_SENSOR) {
                    requireActivity().requestedOrientation =
                        ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
                }
            }
        })

        val youtubePlayerListener = object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                this@WebViewFragment.youTubePlayer = youTubePlayer
                val videoId = extractVideoId(urlIntro)
                youTubePlayer.loadOrCueVideo(lifecycle, videoId, 0f)
            }
        }

        val iFramePlayerOptions = IFramePlayerOptions.Builder()
            .controls(1)
            .fullscreen(1)
            .build()

        youtubePlayerView.enableAutomaticInitialization = false
        youtubePlayerView.initialize(youtubePlayerListener, iFramePlayerOptions)
    }

    private fun extractVideoId(youtubeUrl: String?): String {
        // Contoh cara ekstraksi ID video dari URL YouTube lengkap
        val pattern = Pattern.compile("https?://.*(?:youtu\\.be/|v/|e/|u/\\w+/|embed/|v=)([^#&?]*).*")
        val matcher = pattern.matcher(youtubeUrl.toString())

        return if (matcher.matches()) {
            matcher.group(1)!!
        } else {
            ""
        }
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (!isFullScreen) {
                youTubePlayer.toggleFullscreen()
            }
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (isFullScreen) {
                youTubePlayer.toggleFullscreen()
            }
        }

    }


    override fun onResume() {
        super.onResume()

        val bottomNavigationView: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.GONE
    }
}