package com.example.thefinalproject.network.model


import com.google.gson.annotations.SerializedName

data class DataCategory(
    @SerializedName("category")
    val category: String?,
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
    @SerializedName("title")
    val title: String?,
    @SerializedName("totalModule")
    val totalModule: Int?,
    @SerializedName("type")
    val type: String?
)