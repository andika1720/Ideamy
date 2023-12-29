package com.example.thefinalproject.network.model.course

import com.google.gson.annotations.SerializedName

data class FilterResponse(
    @SerializedName("data")
    val `data`: List<DataCategory>,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)