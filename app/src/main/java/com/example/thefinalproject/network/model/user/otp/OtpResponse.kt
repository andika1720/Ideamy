package com.example.thefinalproject.network.model.user.otp


import com.google.gson.annotations.SerializedName

data class OtpResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)