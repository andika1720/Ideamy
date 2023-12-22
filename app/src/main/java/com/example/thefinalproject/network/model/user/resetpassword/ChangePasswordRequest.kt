package com.example.thefinalproject.network.model.user.resetpassword


import com.google.gson.annotations.SerializedName

data class ChangePasswordRequest(
    @SerializedName("newPassword")
    val password: String,
    @SerializedName("password")
    val newPassword: String
)