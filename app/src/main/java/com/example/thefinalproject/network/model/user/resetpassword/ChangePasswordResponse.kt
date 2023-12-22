package com.example.thefinalproject.network.model.user.resetpassword


import com.google.gson.annotations.SerializedName

data class ChangePasswordResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)