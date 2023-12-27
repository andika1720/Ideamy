package com.example.thefinalproject.network.model.user.getuser


import com.google.gson.annotations.SerializedName

data class DataPutUser(
    @SerializedName("address")
    val address: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("encryptedPassword")
    val encryptedPassword: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phoneNumber")
    val phoneNumber: String?,
    @SerializedName("role")
    val role: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)