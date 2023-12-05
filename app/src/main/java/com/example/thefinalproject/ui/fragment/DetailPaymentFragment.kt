package com.example.thefinalproject.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.thefinalproject.R
import com.example.thefinalproject.databinding.FragmentDetailPaymentBinding
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.network.model.DataCourse
import com.example.thefinalproject.network.model.DetailResponse
import com.example.thefinalproject.util.Status
import com.example.thefinalproject.util.Utils
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.android.ext.android.inject

class DetailPaymentFragment : Fragment() {

    private lateinit var btnBankTransfer: Button
    private lateinit var btnCreditCard: Button
    private lateinit var hiddenViewCardCredit: CardView

    private lateinit var binding: FragmentDetailPaymentBinding
    private val viewModel: ViewModelAll by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailPaymentBinding.inflate(inflater ,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arg =arguments?.getString("selectedId")
        detailPayment(arg.toString())

        btnBankTransfer = view.findViewById(R.id.btn_bankTransfer)
        btnCreditCard = view.findViewById(R.id.btn_cardCredit)
        hiddenViewCardCredit = view.findViewById(R.id.card_crediCard)

        btnCreditCard.setOnClickListener {
            if (hiddenViewCardCredit.visibility == View.VISIBLE) {
                hiddenViewCardCredit.visibility = View.GONE
            } else {
                hiddenViewCardCredit.visibility = View.VISIBLE
            }
        }

        binding.btnBeliSekarang.setOnClickListener {
            botSheetPaymentSuccess()
        }
    }

    private fun botSheetPaymentSuccess(){
      try {
          val dialog = BottomSheetDialog(requireContext())
          val view = layoutInflater.inflate(R.layout.botsheet_payment_succses,null)
          val btnMulaiBelajar = view.findViewById<Button>(R.id.btn_to_class_botshet)
          val btnClose = view.findViewById<ImageView>(R.id.close_botsheet_payment)
          btnMulaiBelajar.setOnClickListener {
              dialog.dismiss()
              botSheetOnboard()
          }
          btnClose.setOnClickListener {
              dialog.dismiss()
          }
          dialog.setCanceledOnTouchOutside(false)
          dialog.setContentView(view)
          dialog.show()
      }catch (e: Exception) {
          Log.e("showbotPayment", "ErrorBotsheet", e)
      }
    }
    private fun botSheetOnboard() {
        try {
            val dialog = BottomSheetDialog(requireContext())
            val view = layoutInflater.inflate(R.layout.botsheet_mulaibelajar, null)
            val btnClose = view.findViewById<ImageView>(R.id.close_botsheet_mulaibelajar)
            val btnIkutiBelajar = view.findViewById<Button>(R.id.btn_to_class_botshet)
            btnClose.setOnClickListener {
                dialog.dismiss()
            }

            btnIkutiBelajar.setOnClickListener {

            }
            dialog.setCanceledOnTouchOutside(false)
            dialog.setContentView(view)
            dialog.show()
        } catch (e: Exception) {
            Log.e("YourFragment", "Error in showBotsheet", e)
        }
    }

    private fun detailPayment(courseId: String) {
        viewModel.getDataById(courseId).observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {

                    it.data?.let { data -> showData(data) }
                }

                Status.ERROR -> {
                    Log.d("Error", "Error Occurred!")
                }

                Status.LOADING -> {
                    Log.d("TESTGETDATA", it.data.toString())
                }
            }
        })

    }

    private fun showData(data: DetailResponse) {
        val course: DataCourse? = data.data
        Glide.with(this)
            .load(course?.image)
            .fitCenter()
            .into(binding.imageView2)
        val hargaAwal: Int? = course?.price
        val ppn: Double? = course?.price?.times(0.11)
        val totalHarga: Int? = hargaAwal?.plus(ppn!!.toInt())
        binding.tvTittleCourse.text = course?.category
        binding.tvHarga.text = "${Utils.formatCurrency(hargaAwal)}"
        binding.tvTotalBayar.text= "${Utils.formatCurrency(totalHarga)}"

        binding.tvPpn.text = "${Utils.formatCurrency(ppn?.toInt())}"
        binding.tvTopicCourse.text = course?.title
        binding.tvAuthorCourse.text = course?.creator
    }
}