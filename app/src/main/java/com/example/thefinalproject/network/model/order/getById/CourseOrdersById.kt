package com.example.thefinalproject.network.model.order.getById


import com.google.gson.annotations.SerializedName

data class CourseOrdersById(
    @SerializedName("category")
    val category: String?,
    @SerializedName("creator")
    val creator: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("level")
    val level: String?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("rating")
    val rating: Double?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("type")
    val type: String?
)