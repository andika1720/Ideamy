package com.example.thefinalproject.network.model.mycourse


import com.google.gson.annotations.SerializedName

data class MyCourseResponse(
    @SerializedName("data")
    val `data`: DataMyCourse?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)