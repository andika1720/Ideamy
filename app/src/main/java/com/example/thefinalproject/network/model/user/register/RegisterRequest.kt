package com.example.thefinalproject.network.model.user.register

import com.google.gson.annotations.SerializedName

class RegisterRequest (
    @SerializedName("name")
    val name: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("phoneNumber")
    val phoneNumber: String?,
    @SerializedName("encryptedPassword")
    val encryptedPassword: String?

)