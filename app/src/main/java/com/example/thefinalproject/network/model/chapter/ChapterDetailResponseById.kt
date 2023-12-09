package com.example.thefinalproject.network.model.chapter


import com.google.gson.annotations.SerializedName

data class ChapterDetailResponseById(
    @SerializedName("data")
    val `data`: DataChapterById?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)