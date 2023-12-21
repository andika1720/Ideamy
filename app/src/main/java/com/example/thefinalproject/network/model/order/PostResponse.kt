package com.example.thefinalproject.network.model.order


import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("data")
    val `data`: DataPost?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)