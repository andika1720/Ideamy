package com.example.thefinalproject.network.model.user.updateprofile


import com.google.gson.annotations.SerializedName

data class ReqNewUser(
    val address: String?,
    val country: String?,
    val createdAt: String?,
    val email: String?,
    val encryptedPassword: String?,
    val id: String?,
    val image: String?,
    val name: String?,
    val phoneNumber: String?,
    val role: String?,
    val updatedAt: String?
)