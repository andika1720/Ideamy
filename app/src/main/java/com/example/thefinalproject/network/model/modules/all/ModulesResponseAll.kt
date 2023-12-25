package com.example.thefinalproject.network.model.modules.all


import com.google.gson.annotations.SerializedName

data class ModulesResponseAll(
    @SerializedName("data")
    val `data`: List<DataModulesNew?>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)