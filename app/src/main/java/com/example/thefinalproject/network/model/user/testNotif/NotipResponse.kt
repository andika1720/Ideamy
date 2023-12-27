package com.example.thefinalproject.network.model.user.testNotif


import com.google.gson.annotations.SerializedName

data class NotipResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)