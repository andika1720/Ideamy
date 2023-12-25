package com.example.thefinalproject.network.model.modules


import com.google.gson.annotations.SerializedName

data class DataModules1(
    @SerializedName("chapterId")
    val chapterId: String?,
    @SerializedName("CourseChapter")
    val courseChapter: CourseChapter?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("video")
    val video: String?
)