package com.example.thefinalproject.network.model.module


import com.google.gson.annotations.SerializedName

data class ModuleResponse(
    @SerializedName("data")
    val `data`: List<DataModule?>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)