package com.example.thefinalproject.ui.fragment.itemPage.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.thefinalproject.R
import com.example.thefinalproject.databinding.FragmentDetailcourseTentangBinding
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.network.model.course.DataCourseById
import com.example.thefinalproject.network.model.course.DetailResponse
import com.example.thefinalproject.util.Status
import com.example.thefinalproject.util.Utils
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject

class DetailcourseTentangFragment : Fragment() {
    private var _binding: FragmentDetailcourseTentangBinding? = null
    private val binding get() = _binding!!
    private val viewMode: ViewModelAll by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailcourseTentangBinding.inflate(inflater, container, false)
        val bottomNavigationView: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.GONE

        val args = arguments?.getString("selectedId")
        Log.d("FragmentTag", "selectedIdTentang: $args")

        showTentang(null,args.toString())


        binding.btnJoinTelegram.setOnClickListener {
            args?.let { link ->
                if (link.isNotBlank()) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                    startActivity(intent)
                } else {
                    Utils.toastMessage(requireContext(), "Link telegram tidak tersedia")
                }
            } ?: run {
                Utils.toastMessage(requireContext(), "Link telegram tidak tersedia")
            }
        }

        return binding.root
    }

    private fun showTentang(token:String?, id: String) {
        viewMode.getDataById("Bearer $token",id).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {

                    it.data?.let { data -> showData(data) }
                }

                Status.ERROR -> {
                    Log.d("Error", "Error Occurred!")
                }

                Status.LOADING -> {
                    Log.d("DataUser", it.data.toString())
                }
            }
        }
    }
    private fun showData(data: DetailResponse) {
        val courseData: DataCourseById? = data.data
        val audienceList = courseData?.audience
        val formattedAudience = StringBuilder()
        audienceList?.forEachIndexed { index, audience ->
            formattedAudience.append("${index + 1}. $audience\n")
        }

        binding.tvDeskripsiTentang.text = courseData?.description
        binding.textView4.text = formattedAudience.toString()
    }
}
