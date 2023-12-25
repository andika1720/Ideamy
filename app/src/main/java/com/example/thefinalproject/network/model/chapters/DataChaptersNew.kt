package com.example.thefinalproject.network.model.chapters


import com.google.gson.annotations.SerializedName

data class DataChaptersNew(
    @SerializedName("chapterNumber")
    val chapterNumber: Int?,
    @SerializedName("courseId")
    val courseId: String?,
    @SerializedName("duration")
    val duration: Int?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("title")
    val title: String?
)