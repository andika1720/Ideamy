package com.example.thefinalproject.network.model.user.testNotif


import com.google.gson.annotations.SerializedName

data class Notification(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("title")
    val title: String
)