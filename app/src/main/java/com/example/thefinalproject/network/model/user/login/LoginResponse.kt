package com.example.thefinalproject.network.model.user.login


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?

)