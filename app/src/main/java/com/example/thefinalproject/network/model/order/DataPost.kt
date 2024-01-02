package com.example.thefinalproject.network.model.order


import com.example.thefinalproject.network.model.mycourse.Course
import com.google.gson.annotations.SerializedName

data class DataPost(
    @SerializedName("cardHolderName")
    val cardHolderName: Any?,
    @SerializedName("cardNumber")
    val cardNumber: Any?,
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
    val userId: String?,
    @SerializedName("Course")
    val course: Course?
)