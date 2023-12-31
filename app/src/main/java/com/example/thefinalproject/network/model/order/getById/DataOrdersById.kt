package com.example.thefinalproject.network.model.order.getById


import com.google.gson.annotations.SerializedName

data class DataOrdersById(
    @SerializedName("cardHolderName")
    val cardHolderName: Any?,
    @SerializedName("cardNumber")
    val cardNumber: Any?,
    @SerializedName("Course")
    val course: CourseOrdersById?,
    @SerializedName("courseId")
    val courseId: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("cvv")
    val cvv: Any?,
    @SerializedName("expiredDateAt")
    val expiredDateAt: String?,
    @SerializedName("expiryDate")
    val expiryDate: Any?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("paymentMethod")
    val paymentMethod: Any?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("userId")
    val userId: String?
)