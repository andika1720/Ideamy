package com.example.thefinalproject.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.example.thefinalproject.adapter.AdapterCategory
import com.example.thefinalproject.adapter.AdapterPageFragment
import com.example.thefinalproject.databinding.FragmentMyClassBinding
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.network.model.course.CategoryResponse
import com.example.thefinalproject.ui.fragment.itemPage.class1.InProgressKelasFragment
import com.example.thefinalproject.ui.fragment.itemPage.class1.SelesaiFragment
import com.example.thefinalproject.ui.fragment.itemPage.class1.SemuaKelasClass
import com.example.thefinalproject.util.Status
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.ext.android.inject

class MyClassFragment : Fragment() {
    private var _binding: FragmentMyClassBinding? = null
    private val binding get() = _binding!!
    private val viewMode: ViewModelAll by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        _binding = FragmentMyClassBinding.inflate(layoutInflater, container, false)

        fetchCategory(null)
        val fragmentList = arrayListOf(SemuaKelasClass(), InProgressKelasFragment(), SelesaiFragment())
        val titleFragment = arrayListOf("Semua Kelas", "In Progress", "Selesai")
        binding.apply {
            viewpageClass.adapter = AdapterPageFragment(fragmentList, requireActivity().supportFragmentManager, lifecycle)
            TabLayoutMediator(tabLayoutClass, viewpageClass) { tab, position ->
                tab.text = titleFragment[position]
            }.attach()
        }



        return binding.root
    }


    private fun fetchCategory(category: String?) {
        viewMode.getAllCategory(category).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    showCategory(it.data)

                    binding.progressbarCategory.isVisible = false

                }

                Status.ERROR -> {
                    binding.progressbarCategory.isVisible = false
                    Log.e("ERROR", it.message.toString())
                }

                Status.LOADING -> {
                    binding.progressbarCategory.isVisible = true
                }
            }
        }
    }

    private fun showCategory(data: CategoryResponse?) {
        val uniqueCategories = data?.data?.distinctBy { it.category }
        val adapter = AdapterCategory(null)
        adapter.sendCategory(uniqueCategories ?: emptyList())
        binding.rvCategory.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.rvCategory.adapter = adapter
    }
}