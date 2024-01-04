package com.example.thefinalproject.network.model.course


import com.google.gson.annotations.SerializedName


data class DataCourseById(
    @SerializedName("audience")
    val audience: List<String?>?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("chapters")
    val chapters: List<ChapterById?>?,
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
    @SerializedName("statusPayment")
    val statusPayment: Boolean,
    @SerializedName("totalDuration")
    val totalDuration: Int?,
    @SerializedName("totalModule")
    val totalModule: Int?,
    @SerializedName("type")
    val type: String?
)