package com.example.thefinalproject.ui.fragment.botsheet

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.thefinalproject.adapter.AdapterCategory
import com.example.thefinalproject.databinding.FragmentBottomSheetDetailCategoryBinding
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.network.model.course.CategoryResponse
import com.example.thefinalproject.util.Status
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.inject

class BottomSheetDetailCategory : BottomSheetDialogFragment() {
    private var _binding: FragmentBottomSheetDetailCategoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var categoryAdapter: AdapterCategory
    private val viewModel: ViewModelAll by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      _binding = FragmentBottomSheetDetailCategoryBinding.inflate(inflater, container, false)


      fetchCategory(null)
      return binding.root
    }


    private fun fetchCategory(category: String?) {
        viewModel.getAllCategory(category).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    showCategory(it.data)
                }
                Status.ERROR -> {

                    Log.e("ERROR", it.message.toString())
                }

                Status.LOADING -> {

                }
            }
        }
    }
    private fun showCategory(data: CategoryResponse?) {
        val uniqueCategories = data?.data?.distinctBy { it.category }
        val adapter = AdapterCategory(null)
        adapter.sendCategory(uniqueCategories ?: emptyList())
        binding.recyclerDetail.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.recyclerDetail.adapter = adapter
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}