package com.example.thefinalproject.network.model.order.getById


import com.google.gson.annotations.SerializedName

data class OrderResponseById(
    @SerializedName("data")
    val `data`: DataOrdersById?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)