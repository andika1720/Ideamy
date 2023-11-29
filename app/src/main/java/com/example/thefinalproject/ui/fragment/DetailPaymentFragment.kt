package com.example.thefinalproject.ui.fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.example.thefinalproject.R

class DetailPaymentFragment : Fragment() {

    private lateinit var btnBankTransfer: Button
    private lateinit var btnCreditCard: Button
    private lateinit var hiddenViewCardCredit: CardView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
    }



}