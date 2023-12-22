package com.example.thefinalproject.network.model.user.forgotpassword.postdata


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep


data class PostForgotPassRequest(
    val email: String?
)