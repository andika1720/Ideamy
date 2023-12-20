package com.example.thefinalproject.network.model.user.otp.resendotp


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ResendOtpResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)