package com.example.thefinalproject.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thefinalproject.adapter.AdapterNotification
import com.example.thefinalproject.databinding.FragmentNotifikasiBinding
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.network.model.user.testNotif.NotipResponse
import com.example.thefinalproject.util.SharePref
import com.example.thefinalproject.util.Status
import org.koin.android.ext.android.inject


class NotifikasiFragment : Fragment() {
    private lateinit var binding: FragmentNotifikasiBinding
    private val viewModel: ViewModelAll by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val savedToken = SharePref.getPref(SharePref.Enum.PREF_NAME.value)
        // Inflate the layout for this fragment
        binding = FragmentNotifikasiBinding.inflate(inflater, container, false)
        fetchNotification(savedToken)
        return binding.root
    }



    private fun setUpAdaoterNotifikasi(data:NotipResponse?) {
        val haha = data?.data?.notifications
        binding.rvNotifikasi.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNotifikasi.adapter = AdapterNotification(haha!!)
    }

    private fun fetchNotification(token: String?) {
        viewModel.getAllNotification(token).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    setUpAdaoterNotifikasi(it.data)
                    Log.e("succes", "YESSS")
                }

                Status.ERROR -> {
                    Log.e("error", it.message.toString())
                }

                Status.LOADING -> {
                    Log.e("load", "Loading")
                }
            }
        }
    }
}