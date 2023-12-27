package com.example.thefinalproject.network.model.user.testNotif


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("email")
    val email: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("notifications")
    val notifications: List<Notification>,
    @SerializedName("role")
    val role: String
)