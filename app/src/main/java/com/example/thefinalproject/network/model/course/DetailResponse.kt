package com.example.thefinalproject.network.model.course



import com.google.gson.annotations.SerializedName



data class DetailResponse(
    @SerializedName("data")
    val `data`: DataCourseById,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)