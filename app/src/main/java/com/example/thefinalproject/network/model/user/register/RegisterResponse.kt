package com.example.thefinalproject.network.model.user.register


import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("message")
    val message: String?
)