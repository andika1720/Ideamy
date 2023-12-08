package com.example.thefinalproject.network.model.user.login

import com.google.gson.annotations.SerializedName

class LoginRequest (
    @SerializedName("email")
    val email: String?,
    @SerializedName("phoneNumber")
    val phoneNumber: String?,
    @SerializedName("encryptedPassword")
    val encryptedPassword: String?
)