package com.example.thefinalproject.ui.fragment.itemPage.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thefinalproject.R
import com.example.thefinalproject.adapter.dataAdapterDetail.AdapterDetail
import com.example.thefinalproject.databinding.FragmentDetailcourseMateriBinding
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.network.model.chapters.DataChapters1
import com.example.thefinalproject.network.model.modules.DataModules1
import com.example.thefinalproject.util.Status
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import org.koin.android.ext.android.inject

class MateriKelasTESTER: Fragment() {
    private lateinit var binding: FragmentDetailcourseMateriBinding
    private val viewmodel: ViewModelAll by inject()
    private var completedRequests = 0
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
        val bottomNavigationView: BottomNavigationView =
            requireActivity().findViewById(R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.GONE


        // Inisialisasi RecyclerView dan adapter



        val selectedChapterId = arguments?.getString("chapterId")
        val selectedModuleId = arguments?.getString("moduleId")
        val arg = arguments?.getString("selectedId")
        Log.d("FragmentTag", "selectedId: $arg")


//        getCourseDetail(arg.toString())
        if (selectedChapterId != null) {
            getChapters(selectedChapterId)
            Log.d("FragmentTag", "selectedChapter: $selectedChapterId")
        }

        if (selectedModuleId != null) {
            getModules(selectedModuleId)
            Log.d("FragmentTag", "selectedModule: $selectedModuleId")
        }

    }

    private fun getChapters(id: String) {
        viewmodel.getChapterById(id).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.e("cek datatersedia", Gson().toJson(it.data))

                    val chapterData = it.data?.data
                    chapterData?.let { dataChapters1 ->
                        if (dataChapters1 != null) {
                            materiList.add(dataChapters1)

                        }
                    }
                    adapter = AdapterDetail(materiList)
                    binding.rvMateri.adapter = adapter
                    binding.rvMateri.layoutManager = LinearLayoutManager(
                        requireContext(), LinearLayoutManager.VERTICAL, false
                    )

                }

                Status.ERROR -> {
                    Log.e("cek error", it.message.toString())
                }

                Status.LOADING -> {

                }
            }

        }
    }


    private fun getModules(chapterId: String) {
        viewmodel.getModulesById(chapterId).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.e("cek datatersedia", Gson().toJson(it.data))


                    val modulesData = it.data?.data

                    modulesData?.let { dataModules1 ->
                        if (dataModules1 != null) {
                            materiList.add(dataModules1)

                        }
                    }


                    adapter = AdapterDetail(materiList,
                        clickListener = { url ->
                            val bundle = bundleOf("youtube" to url)
                            findNavController().navigate(R.id.webViewFragment, bundle)
                        })

                }

                Status.ERROR -> {
                    Log.e("cek error", it.message.toString())
                }

                Status.LOADING -> {

                }
            }

        }
    }
    private fun checkIfBothRequestsCompleted() {
        if (completedRequests == 2) {
            // Sort materiList to ensure chapters are displayed before modules
            materiList.sortBy {
                when (it) {
                    is DataChapters1 -> 0
                    is DataModules1 -> 1
                    else -> throw IllegalArgumentException("Undefined view type")
                }
            }

            adapter = AdapterDetail(materiList,
                clickListener = { url ->
                    val bundle = bundleOf("youtube" to url)
                    findNavController().navigate(R.id.webViewFragment, bundle)
                })

            binding.rvMateri.adapter = adapter
            binding.rvMateri.layoutManager = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

}