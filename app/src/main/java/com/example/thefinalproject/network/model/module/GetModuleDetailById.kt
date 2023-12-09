package com.example.thefinalproject.network.model.module


import com.google.gson.annotations.SerializedName

data class GetModuleDetailById(
    @SerializedName("data")
    val `data`: DataModuleById?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)