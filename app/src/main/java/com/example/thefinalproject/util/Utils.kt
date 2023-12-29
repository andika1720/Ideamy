package com.example.thefinalproject.util

import android.content.Context
import android.widget.Toast
import java.text.DecimalFormat

object Utils {
    fun formatCurrency(amount: Int?): String {
        if (amount == null) {
            return ""
        }
        val decimal = DecimalFormat("#,###")
        return "Rp. " + decimal.format(amount)
    }

        fun toastMessage(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

}