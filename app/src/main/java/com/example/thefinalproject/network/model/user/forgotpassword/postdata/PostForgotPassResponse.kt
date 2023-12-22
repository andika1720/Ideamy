package com.example.thefinalproject.network.model.user.forgotpassword.postdata


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class PostForgotPassResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)