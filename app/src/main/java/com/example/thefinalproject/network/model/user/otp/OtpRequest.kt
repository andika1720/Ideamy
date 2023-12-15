package com.example.thefinalproject.network.model.user.otp


import com.google.gson.annotations.SerializedName

data class OtpRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("otp")
    val otp: String
)