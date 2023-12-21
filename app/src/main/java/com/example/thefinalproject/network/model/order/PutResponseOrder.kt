package com.example.thefinalproject.network.model.order


import com.google.gson.annotations.SerializedName

data class PutResponseOrder(
    @SerializedName("data")
    val `data`: DataPutOrder?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)