package com.example.thefinalproject.network.model


import com.google.gson.annotations.SerializedName

data class ListResponse(
    @SerializedName("data")
    val `data`: List<DataCategory>,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)