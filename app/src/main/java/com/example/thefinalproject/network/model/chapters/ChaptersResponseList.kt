package com.example.thefinalproject.network.model.chapters


import com.google.gson.annotations.SerializedName

data class ChaptersResponseList(
    @SerializedName("data")
    val `data`: List<DataChaptersNew?>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)