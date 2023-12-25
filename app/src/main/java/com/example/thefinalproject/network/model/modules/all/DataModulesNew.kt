package com.example.thefinalproject.network.model.modules.all


import com.google.gson.annotations.SerializedName

data class DataModulesNew(
    @SerializedName("chapterId")
    val chapterId: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("video")
    val video: String?
)