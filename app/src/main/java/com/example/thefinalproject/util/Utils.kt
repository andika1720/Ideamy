package com.example.thefinalproject.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast
import com.example.thefinalproject.R
import com.google.android.material.bottomnavigation.BottomNavigationView
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

        fun setUpBottomNavigation(activity: Activity?, isGone: Boolean) {
            val bottomNav = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            if (isGone) {
                bottomNav?.visibility = View.GONE
            } else {
                bottomNav?.visibility = View.VISIBLE
            }
        }

        fun isValidUsername(username: String): Boolean {
            return username.matches(Regex("[a-zA-Z0-9]+"))
        }

        fun isValidEmail(email: String): Boolean {
            val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
            return email.matches(emailRegex.toRegex())
        }

        fun isValidPhoneNumber(phoneNumber: String): Boolean {
            return phoneNumber.matches(Regex("[0-9]+")) && phoneNumber.length >= 10
        }

        fun isValidPassword(password: String): Boolean {
            return password.length >= 6
        }

}