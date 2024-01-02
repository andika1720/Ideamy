package com.example.thefinalproject.network.model.user.forgotpassword.putdata



data class PutForgotPassRequest(
    val email: String?,
    val otp: String?,
    val password: String?
)