package com.example.thefinalproject.network.model.chapter


import com.google.gson.annotations.SerializedName

data class DataChapterById(
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