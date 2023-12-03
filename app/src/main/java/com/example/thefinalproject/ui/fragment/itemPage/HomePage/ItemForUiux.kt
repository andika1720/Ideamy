package com.example.thefinalproject.ui.fragment.itemPage.HomePage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thefinalproject.adapter.ForItemHomePage.AdapterAllKursusPopuler
import com.example.thefinalproject.databinding.FragmentItemSemuaKelasBinding
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.network.model.ListResponse
import com.example.thefinalproject.util.Status
import org.koin.android.ext.android.inject

class ItemForUiux:Fragment() {
    private lateinit var binding: FragmentItemSemuaKelasBinding
    private val viewMode : ViewModelAll by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentItemSemuaKelasBinding.inflate(inflater, container, false)
        fetchList()
        return binding.root
    }


    private fun fetchList() {
        viewMode.getAllList().observe(viewLifecycleOwner) {
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
        val adapter = AdapterAllKursusPopuler()

        val filteredList = data?.data?.filter { it.id =="7b20fea8-cccf-4673-b818-2826ca149f5f" }
        adapter.sendList(filteredList?: emptyList())
        binding.rvHomeAllCategory.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvHomeAllCategory.adapter = adapter
    }
}