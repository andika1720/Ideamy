package com.example.thefinalproject.network.model.module


import com.google.gson.annotations.SerializedName

data class DataModuleById(
    @SerializedName("chapterId")
    val chapterId: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("video")
    val video: String?
)