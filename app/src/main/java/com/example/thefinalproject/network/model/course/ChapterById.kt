package com.example.thefinalproject.network.model.course


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

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
    val modules: List<ModuleById?>?,
    @SerializedName("title")
    val title: String?
)