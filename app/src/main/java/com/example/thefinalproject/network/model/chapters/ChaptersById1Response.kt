package com.example.thefinalproject.network.model.chapters


import com.google.gson.annotations.SerializedName

data class ChaptersById1Response(
    @SerializedName("data")
    val `data`: DataChapters1?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)