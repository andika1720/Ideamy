package com.example.thefinalproject.network.model.course


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class DetailResponse(
    @SerializedName("data")
    val `data`: DataCourseById,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)