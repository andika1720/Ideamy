package com.example.thefinalproject.network.model.user.otp


import com.google.gson.annotations.SerializedName

data class OtpRequest(

    val email: String,
    val otp: String
)