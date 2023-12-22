package com.example.thefinalproject.network.model.user.forgotpassword.putdata


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

data class PutForgotPassRequest(
    val email: String?,
    val otp: String?,
    val password: String?
)