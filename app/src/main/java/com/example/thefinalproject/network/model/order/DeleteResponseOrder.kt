package com.example.thefinalproject.network.model.order


import com.google.gson.annotations.SerializedName

data class DeleteResponseOrder(
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)