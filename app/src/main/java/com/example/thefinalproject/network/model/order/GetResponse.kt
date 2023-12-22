package com.example.thefinalproject.network.model.order


import com.google.gson.annotations.SerializedName

data class GetResponse(
    @SerializedName("data")
    val `data`: List<DataPost>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)