package com.example.thefinalproject.network.model.user.getuser


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("address")
    val address: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("encryptedPassword")
    val encryptedPassword: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phoneNumber")
    val phoneNumber: String?,
    @SerializedName("role")
    val role: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)