package com.example.thefinalproject.ui.fragment.itemPage.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.thefinalproject.databinding.FragmentDetailcourseTentangBinding
import com.example.thefinalproject.util.Utils

class DetailcourseTentangFragment : Fragment() {
    private var _binding: FragmentDetailcourseTentangBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailcourseTentangBinding.inflate(inflater, container, false)

        val description = arguments?.getString("description")
        val telegramLink = arguments?.getString("telegramLink")

        binding.tvDeskripsiTentang.text = description

        binding.btnJoinTelegram.setOnClickListener {
            telegramLink?.let { link ->
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
}
