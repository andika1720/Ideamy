package com.example.thefinalproject.ui.fragment

import com.example.thefinalproject.util.SharePref
import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.thefinalproject.R
import com.example.thefinalproject.databinding.FragmentDetailPaymentBinding
import com.example.thefinalproject.mvvm.viewmmodel.AuthViewModel
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.network.model.course.DataCourseById
import com.example.thefinalproject.network.model.course.DetailResponse
import com.example.thefinalproject.network.model.order.DataPutOrder
import com.example.thefinalproject.network.model.order.PutResponseOrder
import com.example.thefinalproject.network.model.order.RequestPutOrder
import com.example.thefinalproject.util.Status
import com.example.thefinalproject.util.Utils
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.android.ext.android.inject

class DetailPaymentFragment : Fragment() {

    private lateinit var btnBankTransfer: Button
    private lateinit var btnCreditCard: Button
    private lateinit var hiddenViewCardCredit: CardView

    private var _binding: FragmentDetailPaymentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ViewModelAll by inject()
    private val authViewModel: AuthViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailPaymentBinding.inflate(inflater ,container,false)
        // Inflate the layout for this fragment

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvArrowBack.setOnClickListener {
            findNavController().popBackStack()
        }
        val arg =arguments?.getString("selectedId")
        val getId = arguments?.getString("orderId")
        val savedToken = SharePref.getPref(SharePref.Enum.PREF_NAME.value)

        detailPayment(savedToken.toString(),arg.toString())

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

            val cardHolderName = binding.etCardHolderName.text.toString()
            val cardNumber = binding.etCardNumber.text.toString()
            val cvv = binding.etCvv.text.toString()
            val expiryDate = binding.etExpiryDate.text.toString()
            val paymentMethod = "Credit Card"
            updatePayment(savedToken.toString(), getId.toString(), cardHolderName, cardNumber, cvv, expiryDate, paymentMethod)

        }
    }

    @SuppressLint("InflateParams")
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
    @SuppressLint("InflateParams")
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
                val courseId = arguments?.getString("selectedId")
                    val bundle = Bundle().apply {
                        putString("selectedId", courseId)
                    }
                    findNavController().navigate(R.id.action_detailPaymentFragment_to_detailCourse, bundle)
                dialog.dismiss()
            }
            dialog.setCanceledOnTouchOutside(false)
            dialog.setContentView(view)
            dialog.show()
        } catch (e: Exception) {
            Log.e("YourFragment", "Error in showBotsheet", e)
        }
    }

    private fun detailPayment(token:String?,courseId: String) {
        viewModel.getDataById("Bearer $token",courseId).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {

                    it.data?.let { data -> showData(data) }

                    Log.d("GetDataPayment", it.data.toString())
                }

                Status.ERROR -> {
                    Log.d("Error", "Error Occurred!")
                }

                Status.LOADING -> {
                    Log.d("load", it.data.toString())
                }
            }
        }

    }

    private fun showData(data: DetailResponse) {
        val course: DataCourseById? = data.data
        Glide.with(this)
            .load(course?.image)
            .fitCenter()
            .into(binding.imageView2)
        val hargaAwal: Int? = course?.price
        val ppn: Double? = course?.price?.times(0.11)
        val totalHarga: Int? = hargaAwal?.plus(ppn!!.toInt())
        binding.tvTittleCourse.text = course?.category
        binding.tvHarga.text = Utils.formatCurrency(hargaAwal)
        binding.tvTotalBayar.text= Utils.formatCurrency(totalHarga)

        binding.tvPpn.text = Utils.formatCurrency(ppn?.toInt())
        binding.tvTopicCourse.text = course?.title
        binding.tvAuthorCourse.text = course?.creator
    }


    private fun updatePayment(token:String?,id: String,cardHolderName: String?, cardNumber: String?, cvv: String?, expiryDate: String?, paymentMethod: String?) {
        Log.d("PMETHOD", paymentMethod.toString())
        val updateRequest = RequestPutOrder(cardHolderName, cardNumber, cvv, expiryDate, paymentMethod
        )
        authViewModel.updatePayment("Bearer $token",id, updateRequest).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.d("updatePaymentSuc", "Succes")
                    it.data?.let { data -> showUpdatePayment(data) }
                    botSheetPaymentSuccess()
                }

                Status.ERROR -> {
                    Log.d("updatePaymentError", it.message.toString())
                }

                Status.LOADING -> {
                    Log.d("updatPaymentLoad", it.data.toString())
                }
            }
        }

    }
    private fun showUpdatePayment(data: PutResponseOrder) {
        val course: DataPutOrder? = data.data
        binding.tvCardCredit.text = course?.paymentMethod
        binding.etCardNumber.text = Editable.Factory.getInstance().newEditable(course?.cardNumber.toString())
        binding.etCardHolderName.text = Editable.Factory.getInstance().newEditable(course?.cardHolderName.toString())
        binding.etCvv.text =Editable.Factory.getInstance().newEditable(course?.cvv.toString())
        binding.etExpiryDate.text =Editable.Factory.getInstance().newEditable(course?.expiryDate.toString())

    }
}