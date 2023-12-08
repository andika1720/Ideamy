package com.example.thefinalproject.ui.fragment.itemPage.homepage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thefinalproject.R
import com.example.thefinalproject.adapter.foritemhomepage.AdapterKursusPopuler2
import com.example.thefinalproject.databinding.FragmentItemSemuaKelasBinding
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.network.model.ListResponse
import com.example.thefinalproject.util.Status
import org.koin.android.ext.android.inject

class ItemForDataScience:Fragment() {
    private lateinit var binding: FragmentItemSemuaKelasBinding
    private val viewMode : ViewModelAll by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentItemSemuaKelasBinding.inflate(inflater, container, false)
        fetchList(null,"Data Science", null, null,null)
        return binding.root
    }


    private fun fetchList(id: String?,category: String?,level: String?, type: String?,search: String?) {
        viewMode.getFilterCourse(id, category, level, type,search).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    showListHorizontal(it.data)
                    binding.pgbarAll.isVisible = false

                }

                Status.ERROR -> {
                    binding.pgbarAll.isVisible = false
                    Log.e("Errorr", it.message.toString())
                }

                Status.LOADING -> {
                    binding.pgbarAll.isVisible = true
                }
            }


        }
    }


    private fun showListHorizontal(data: ListResponse?) {
        val adapter = AdapterKursusPopuler2(onButtonClick = {
            val bundle = Bundle().apply {
                putString("selectedId", it)
            }
            findNavController().navigate(R.id.action_homeFragment2_to_detailPaymentFragment,bundle)
        })


        adapter.sendList(data?.data ?: emptyList())
        binding.rvHomeAllCategory.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvHomeAllCategory.adapter = adapter
    }
}