package com.example.thefinalproject.network.model.modules


import com.google.gson.annotations.SerializedName

data class CourseChapter(
    @SerializedName("chapterNumber")
    val chapterNumber: Int?
)