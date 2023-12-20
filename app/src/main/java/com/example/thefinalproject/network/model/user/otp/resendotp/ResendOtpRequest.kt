package com.example.thefinalproject.network.model.user.otp.resendotp


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ResendOtpRequest(
    @SerializedName("email")
    val email: String?
)