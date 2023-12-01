package com.example.thefinalproject.ui.fragment.itemPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thefinalproject.adapter.CourseAdapter
import com.example.thefinalproject.databinding.FragmentItemKelasgeratisBinding
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.util.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class ItemKelaspremiumFragment : Fragment() {

    private lateinit var binding: FragmentItemKelasgeratisBinding
    private val viewModelAll: ViewModelAll by viewModel()
    private val courseAdapter: CourseAdapter by lazy { CourseAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemKelasgeratisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeFreeCourses()
    }

    private fun setupRecyclerView() {
        binding.rvKelasGeratis.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = courseAdapter
        }
    }

    private fun observeFreeCourses() {
        viewModelAll.getFilteredCourses(1, emptyList(), emptyList()).observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    resource.data?.let { courses ->
                        courseAdapter.sendList(courses)
                    }
                }
                Status.ERROR -> {
                    // Handle error
                }
                Status.LOADING -> {
                    // Handle loading
                }
            }
        })
    }
}
