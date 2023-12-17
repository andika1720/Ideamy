package com.example.thefinalproject.network.model.gabungan


import com.google.gson.annotations.SerializedName

data class DetailResponse1(
    @SerializedName("data")
    val `data`: DataCourseById1?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)


data class DataCourseById1(
    @SerializedName("audience")
    val audience: List<String?>?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("chapters")
    val chapters: List<ChapterById1>?,
    @SerializedName("creator")
    val creator: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("level")
    val level: String?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("telegram")
    val telegram: String?,
    @SerializedName("rating")
    val rating: Double?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("totalDuration")
    val totalDuration: Int?,
    @SerializedName("totalModule")
    val totalModule: Int?,
    @SerializedName("type")
    val type: String?
)



data class ChapterById1(
    @SerializedName("chapterNumber")
    val chapterNumber: Int,
    @SerializedName("courseId")
    val courseId: String?,
    @SerializedName("duration")
    val duration: Int?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("modules")
    val modules: List<ModuleById1>?,
    @SerializedName("title")
    val title: String?
)



data class ModuleById1(
    @SerializedName("chapterId")
    val chapterId: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("video")
    val video: String?
)