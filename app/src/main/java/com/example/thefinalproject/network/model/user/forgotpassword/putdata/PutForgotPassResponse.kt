package com.example.thefinalproject.network.model.user.forgotpassword.putdata


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class PutForgotPassResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)