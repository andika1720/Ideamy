package com.example.thefinalproject.network.model.user.getuser


import com.google.gson.annotations.SerializedName

data class GetCurrentUser(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)