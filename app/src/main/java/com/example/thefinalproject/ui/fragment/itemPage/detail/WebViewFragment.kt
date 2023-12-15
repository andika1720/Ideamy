package com.example.thefinalproject.ui.fragment.itemPage.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import com.example.thefinalproject.R
import com.example.thefinalproject.adapter.MateriAdapter

class WebViewFragment : Fragment() {
    private lateinit var materiAdapter: MateriAdapter
    private lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_web_view,container,false)
        webView = view.findViewById(R.id.this_web_view)
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true

        val videoUrl = arguments?.getString(ARG_VIDEO_URL)
        if (!videoUrl.isNullOrBlank()){
            webView.loadUrl(videoUrl)
        }else {
            Log.e("Video tidak tersedia", "Loading")
        }
        return view
    }

    companion object {
        private const val ARG_VIDEO_URL = "videoUrl"
        @JvmStatic
        fun newInstance(videoUrl: String) =
            WebViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_VIDEO_URL, videoUrl)
                }
            }
    }
}