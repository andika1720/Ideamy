package com.example.thefinalproject.network.model.course


import com.google.gson.annotations.SerializedName

data class ChapterById(
    @SerializedName("chapterNumber")
    val chapterNumber: Int?,
    @SerializedName("courseId")
    val courseId: String?,
    @SerializedName("duration")
    val duration: Int?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("modules")
    val modules: List<ModuleById>?,
    @SerializedName("title")
    val title: String?
)