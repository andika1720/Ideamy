package com.example.thefinalproject.network.model.order


import com.google.gson.annotations.SerializedName

data class DataPutOrder(
    @SerializedName("cardHolderName")
    val cardHolderName: String?,
    @SerializedName("cardNumber")
    val cardNumber: String?,
    @SerializedName("courseId")
    val courseId: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("cvv")
    val cvv: String?,
    @SerializedName("expiredDateAt")
    val expiredDateAt: String?,
    @SerializedName("expiryDate")
    val expiryDate: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("paymentMethod")
    val paymentMethod: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("userId")
    val userId: String?
)