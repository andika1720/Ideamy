package com.example.thefinalproject.network.model.course


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class ModuleById(
    @SerializedName("chapterId")
    val chapterId: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("video")
    val video: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)