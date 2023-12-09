package com.example.thefinalproject.network.model.chapter


import com.google.gson.annotations.SerializedName

data class ChapterResponse(
    @SerializedName("data")
    val `data`: List<DataChapter?>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)