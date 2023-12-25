package com.example.thefinalproject.network.model.chapters


import com.google.gson.annotations.SerializedName

data class DataChapters1(
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