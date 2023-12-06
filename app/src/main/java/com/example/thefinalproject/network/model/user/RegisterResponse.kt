package com.example.thefinalproject.network.model.user


import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("address")
    val address: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("phoneNumber")
    val phoneNumber: String?
)