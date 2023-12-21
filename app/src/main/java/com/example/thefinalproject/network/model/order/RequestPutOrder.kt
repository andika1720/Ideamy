package com.example.thefinalproject.network.model.order


import com.google.gson.annotations.SerializedName

data class RequestPutOrder(
    val cardHolderName: String?,
    val cardNumber: String?,
    val cvv: String?,
    val expiryDate: String?,
    val paymentMethod: String?
)