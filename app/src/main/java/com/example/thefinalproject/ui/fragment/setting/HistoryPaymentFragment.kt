package com.example.thefinalproject.ui.fragment.setting

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thefinalproject.R
import com.example.thefinalproject.adapter.AdapterOrder
import com.example.thefinalproject.databinding.FragmentHistoryPaymentBinding
import com.example.thefinalproject.mvvm.viewmmodel.AuthViewModel
import com.example.thefinalproject.network.model.order.DataPost
import com.example.thefinalproject.network.model.order.GetResponse
import com.example.thefinalproject.util.SharePref
import com.example.thefinalproject.util.Status
import com.example.thefinalproject.util.Utils
import org.koin.android.ext.android.inject

class HistoryPaymentFragment : Fragment() {
    private var _binding: FragmentHistoryPaymentBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharePref: SharePref
    private val authViewModel: AuthViewModel by inject()
    private lateinit var adapter:AdapterOrder
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryPaymentBinding.inflate(inflater, container, false)
        sharePref = SharePref

        adapter = AdapterOrder(object : AdapterOrder.CourseClick{
            override fun onCourseItemClick(data: DataPost) {
                if(data.status.equals("PENDING")) {
                    val alertDialogBuilder = AlertDialog.Builder(requireContext())
                    alertDialogBuilder.setTitle("Lanjutkan Pembayaran")
                    alertDialogBuilder.setMessage("Apakah anda ingin melanjutkan atau membatalkan pembayaran?")
                    alertDialogBuilder.setPositiveButton("Batalkan Pembayaran") { _, _ ->
                        batalOrder(data.id)
                    }

                    alertDialogBuilder.setNeutralButton("Batal") { _, _ -> }

                    alertDialogBuilder.setNegativeButton("Lanjutkan Pembayaran") { _, _ ->
                        val bundle = Bundle().apply {
                            putString("selectedId", data.courseId)
                            putString("orderId", data.id)
                        }
                        findNavController().navigate(R.id.detailPaymentFragment, bundle)
                    }

                    val alertDialog: AlertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                }else{
                    val bundle = Bundle().apply {
                        putString("selectedId", data.courseId)
                    }
                    findNavController().navigate(R.id.action_historyPaymentFragment_to_detailCourse, bundle)
                }
            }
        })
        binding.rvOrder.layoutManager = LinearLayoutManager(requireContext())
        binding.rvOrder.adapter = adapter
        showOrderCoroutines(sharePref.getPref(SharePref.Enum.PREF_NAME.value))

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_historyPaymentFragment_to_settingFragment2)
        }

        return binding.root
    }

    private fun batalOrder(id: String?) {
        authViewModel.deletePayment(sharePref.getPref(SharePref.Enum.PREF_NAME.value),id).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { _ ->
                        showOrderCoroutines(sharePref.getPref(SharePref.Enum.PREF_NAME.value))
                        Utils.toastMessage(requireContext(), "Berhasil membatalkan pesanan")
                    }
                }
                Status.ERROR -> {
                    Utils.toastMessage(requireContext(), "Gagal membatalkan pesanan : ${it.message.toString()}")
                    Log.d("ErrorPost", it.message.toString())
                }
                Status.LOADING -> {
                    Log.d("loadPost", it.data.toString())
                }
            }
        }
    }

    private fun showOrderCoroutines(token:String?) {
        authViewModel.getOrders(token).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { data -> postOrder(data) }
                }
                Status.ERROR -> {
                    Log.d("ErrorPost", it.message.toString())
                }
                Status.LOADING -> {
                    Log.d("loadPost", it.data.toString())
                }
            }
        }
    }

    private fun postOrder(data: GetResponse) {
        data.data?.let { adapter.sendList(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}