package com.example.thefinalproject.network.model.course


import com.google.gson.annotations.SerializedName

data class ModuleById(
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("done")
    val done: Boolean,
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)