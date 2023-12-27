package com.example.thefinalproject.ui.fragment.itemPage.detail

import android.os.Bundle
import android.provider.ContactsContract.Data
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
import com.example.thefinalproject.adapter.dataAdapterDetail.AdapterDetailTESTER
import com.example.thefinalproject.databinding.FragmentDetailcourseMateriBinding
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.network.model.chapters.DataChapters1
import com.example.thefinalproject.network.model.modules.DataModules1
import com.example.thefinalproject.util.SharePref
import com.example.thefinalproject.util.Status
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import java.util.ArrayList

class MateriKelasTESTER: Fragment(){
    private lateinit var binding: FragmentDetailcourseMateriBinding
    private val viewmodel: ViewModelAll by inject()
    private var completedRequests = 0
    private var totalRequest = 0
    private var completedChapterRequests = 0
    private var completedModuleRequests = 0
    private var totalChapterRequests = 0
    private var totalModuleRequests = 0
    private var materiList: MutableList<Any> = mutableListOf()
    private lateinit var adapter: AdapterDetailTESTER
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
        val savedToken = SharePref.getPref(SharePref.Enum.PREF_NAME.value)



        val selectedChapterId = arguments?.getStringArrayList("chapterIds")
        val selectedModuleId = arguments?.getStringArrayList("moduleIds")
        val arg = arguments?.getString("selectedId")
        Log.d("FragmentTag", "selectedId: $arg")

        selectedChapterId?.forEach { chapterId ->
            totalChapterRequests++
            getChapters(savedToken,chapterId.toString())
            Log.d("FragmentData", "selectedChapter: $selectedChapterId")
        }

        selectedModuleId?.forEach { moduleId ->
            totalModuleRequests++
            getModules(savedToken,moduleId.toString())
            Log.d("FragmentData", "selectedModule: $selectedModuleId")

        }


        adapter = AdapterDetailTESTER(materiList)
        binding.rvMateri.adapter = adapter
        binding.rvMateri.layoutManager = LinearLayoutManager(
            requireContext(),LinearLayoutManager.VERTICAL,false
        )

    }

    private fun getChapters(token:String?,id: String) {
        viewmodel.getChapterById(token,id).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.e("cekDataChaps", "Chapters : $it")

                    val chapterData = it.data?.data
                    chapterData?.let { dataChapters1 ->
                        if (dataChapters1.chapterNumber != null) {
                            materiList.add(dataChapters1)
                            completedChapterRequests++
                            checkAndUpdateAdapter()
                        } else {
                            Log.e("Chapter", "Chapter number is null for id: $id")
                        }
                    }

                }

                Status.ERROR -> {
                    Log.e("cek errorchapter", it.message.toString())
                }

                Status.LOADING -> {

                }
            }

        }
    }


    private fun getModules(token:String?,chapterId: String) {
        viewmodel.getModulesById(token,chapterId).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.e("cekDataModules", "Modules : $it")


                    val modulesData = it.data?.data
                    modulesData?.let { dataModules1 ->
                        if (dataModules1.courseChapter?.chapterNumber != null) {
                            materiList.add(dataModules1)
                            completedChapterRequests++
                            checkAndUpdateAdapter()
                        } else {
                            Log.e("Chapter", "Chapter number is null for id: $id")
                        }
                    }



                }

                Status.ERROR -> {
                    Log.e("cek errormodules", it.message.toString())
                }

                Status.LOADING -> {

                }
            }

        }
    }
    private fun checkAndUpdateAdapter() {
        if (completedChapterRequests == totalChapterRequests && completedModuleRequests == totalModuleRequests) {
            // All requests are completed, update the adapter
            materiList
                .filterIsInstance<DataModules1>()
                .sortedBy { it.courseChapter?.chapterNumber ?: Int.MAX_VALUE }
                .toMutableList()
        }

        binding.rvMateri.adapter = adapter
        binding.rvMateri.layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false
        )

        adapter = AdapterDetailTESTER(materiList,
            clickListener = { url ->
                val bundle = bundleOf("youtube" to url)
                findNavController().navigate(R.id.webViewFragment, bundle)
            })
    }
    private fun checkIfBothRequestsCompleted() {
        completedRequests++
        if (completedRequests == totalRequest) {
            // Sort materiList to ensure chapters are displayed before modules
            materiList.sortBy {
                when (it) {
                    is DataChapters1 -> 0
                    is DataModules1 -> 1
                    else -> throw IllegalArgumentException("Undefined view type")
                }
            }

            adapter = AdapterDetailTESTER(materiList,
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