package com.example.thefinalproject.network.model.user.updateprofile


import com.google.gson.annotations.SerializedName

data class PutDataUser(
    @SerializedName("data")
    val data: DataUser?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)