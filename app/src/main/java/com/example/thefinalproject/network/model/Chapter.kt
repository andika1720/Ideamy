package com.example.thefinalproject.network.model


import com.google.gson.annotations.SerializedName

data class Chapter(
    @SerializedName("chapterNumber")
    val chapterNumber: Int?,
    @SerializedName("courseId")
    val courseId: String?,
    @SerializedName("duration")
    val duration: Int?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("modules")
    val modules: List<Module?>?,
    @SerializedName("title")
    val title: String?
)