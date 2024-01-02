package com.example.thefinalproject.network.model.order



data class RequestPutOrder(
    val cardHolderName: String?,
    val cardNumber: String?,
    val cvv: String?,
    val expiryDate: String?,
    val paymentMethod: String?
)