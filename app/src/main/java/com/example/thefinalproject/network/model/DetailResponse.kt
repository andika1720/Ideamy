package com.example.thefinalproject.network.model


import com.google.gson.annotations.SerializedName

data class DetailResponse(
    @SerializedName("data")
    val `data`: DataDetail?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)