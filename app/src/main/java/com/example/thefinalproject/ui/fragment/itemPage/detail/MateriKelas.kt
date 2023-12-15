package com.example.thefinalproject.ui.fragment.itemPage.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thefinalproject.adapter.MateriAdapter
import com.example.thefinalproject.databinding.FragmentDetailcourseMateriBinding
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.network.model.course.ChapterById
import com.example.thefinalproject.network.model.course.ModuleById
import com.example.thefinalproject.util.Status

class MateriKelas : Fragment() {
    private lateinit var binding: FragmentDetailcourseMateriBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MateriAdapter
    private val mlist: ArrayList<ChapterById> = ArrayList()
    private val vlist: ArrayList<ModuleById> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailcourseMateriBinding.inflate(inflater, container, false)
        binding.rvMateri.setHasFixedSize(true)
        binding.rvMateri.layoutManager = LinearLayoutManager(requireContext())

        val args = arguments?.getString("selectedId")

        recyclerView = binding.rvMateri

        // Inisialisasi adapter
        adapter = MateriAdapter(mlist + vlist)
        recyclerView.adapter = adapter

        val viewMode: ViewModelAll by viewModels()
        fetchDataMateri()

        return binding.root
    }


    private fun fetchDataMateri() {
//        val viewMode: ViewModelAll by viewModels()
//        viewMode.getDataById(id).observe(viewLifecycleOwner, Observer { resource ->
//            when (resource.status) {
//                Status.SUCCESS -> {
//                    val dataCourse = resource.data as? ChapterById
//                    val dataModul = resource.data as? ModuleById
//
//                    dataCourse?.let {
//                        mlist.add(it)
//                    }
//
//                    dataModul?.let {
//                        vlist.add(it)
//                    }
//                    adapter.notifyDataSetChanged()
//                }
//                Status.ERROR -> {
//                    val errorMessage = resource.message ?: "Error Occurred!"
//                    // Tindakan untuk kasus error
//                }
//                Status.LOADING -> {
//                    Log.e("Wait a Minute", "Loading")
//                }
//                else -> {
//                    Log.e("Data not found", "...")
//                }
//            }
//        })
    }
}
