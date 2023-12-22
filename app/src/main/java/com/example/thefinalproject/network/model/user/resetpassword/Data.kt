package com.example.thefinalproject.network.model.user.resetpassword


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("newPassword")
    val password: String,
    @SerializedName("password")
    val newPassword: String
)