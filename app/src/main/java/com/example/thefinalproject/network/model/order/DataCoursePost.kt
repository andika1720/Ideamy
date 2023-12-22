package com.example.thefinalproject.network.model.order


import com.google.gson.annotations.SerializedName

data class DataCoursePost(
    @SerializedName("category")
    val category: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("level")
    val level: String?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("type")
    val type: String?
)