package com.example.thefinalproject.ui.fragment.itemPage.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thefinalproject.R


import com.example.thefinalproject.adapter.dataAdapterDetail.AdapterDetail

import com.example.thefinalproject.databinding.FragmentDetailcourseMateriBinding
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.util.SharePref


import com.example.thefinalproject.util.Status
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import org.koin.android.ext.android.inject

class MateriKelas : Fragment() {
    private lateinit var binding: FragmentDetailcourseMateriBinding
    private val viewmodel:ViewModelAll by inject()
    private val savedToken = SharePref.getPref(SharePref.Enum.PREF_NAME.value)
    private var materiList: MutableList<Any> = mutableListOf()
    private lateinit var adapter: AdapterDetail
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailcourseMateriBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomNavigationView: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.GONE


        val selectedChapterId = arguments?.getString("selectedChapterId")
        val selectedModuleId = arguments?.getString("selectedModuleId")
        val arg = arguments?.getString("selectedId")
        Log.d("FragmentTag", "selectedId: $arg")
        getCourseDetail(arg.toString())
        if (selectedChapterId != null) {
           getCourseDetail(selectedChapterId)
            Log.d("FragmentTag", "selectedChapter: $selectedChapterId")
        }

        if (selectedModuleId != null) {
            getCourseDetail(selectedModuleId)
            Log.d("FragmentTag", "selectedModule: $selectedModuleId")
        }
        // Inisialisasi RecyclerView dan adapter

        adapter = AdapterDetail(materiList)
        binding.rvMateri.adapter = adapter
        binding.rvMateri.layoutManager = LinearLayoutManager(
            requireContext(),LinearLayoutManager.VERTICAL,false
        )


    }
    private fun getCourseDetail( id: String) {
        viewmodel.getDataById1(id).observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS -> {
                    Log.d("cek datatersedia", "chapter :${it.data}")

                    val data = it.data?.data
                    data?.chapters?.forEach {chapter ->
                        if (chapter != null) {
                            materiList.add(chapter)
                            Log.d("dataChaps", "chapter :$chapter")
                            chapter.modules?.forEach {module ->
                                Log.d("dataChaps", "module :$module")
                                materiList.add(module!!)
                            }
                        }
                    }

                    adapter = AdapterDetail(materiList,
                        clickListener  = { url ->
                        getModules(savedToken,url)
                        } )

                    binding.rvMateri.adapter = adapter
                    binding.rvMateri.layoutManager = LinearLayoutManager(
                        requireActivity(),
                        LinearLayoutManager.VERTICAL,
                        false
                    )

                    Log.e("ListMateri",materiList.toString())


                }
                Status.ERROR -> {
                    Log.e("cek error", it.message.toString())
                }

                Status.LOADING -> {

                }
            }
        }
    }

    private fun getModules(token:String?,chapterId: String) {
        viewmodel.getModulesById(token, chapterId).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.e("cekDataModules", "Modules : $it")


                    val modulesData = it.data?.data
                    val linkyoutube = modulesData?.video
                    val vidio = linkyoutube
                    val bundle = bundleOf("youtube" to vidio)
                    findNavController().navigate(R.id.webViewFragment, bundle)
                }

                Status.ERROR -> {
                    Log.e("cek errormodules", it.message.toString())
                }

                Status.LOADING -> {

                }
            }

        }

    }

}


