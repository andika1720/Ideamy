package com.example.thefinalproject.ui.fragment.itemPage.HomePage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thefinalproject.R
import com.example.thefinalproject.databinding.FragmentItemSemuaKelasBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ItemForProductm.newInstance] factory method to
 * create an instance of this fragment.
 */
class ItemForProductm : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentItemSemuaKelasBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentItemSemuaKelasBinding.inflate(inflater, container, false)
        return binding.root
    }
}

