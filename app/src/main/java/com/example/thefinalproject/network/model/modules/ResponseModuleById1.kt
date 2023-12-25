package com.example.thefinalproject.network.model.modules


import com.google.gson.annotations.SerializedName

data class ResponseModuleById1(
    @SerializedName("data")
    val `data`: DataModules1?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)