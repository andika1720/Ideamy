package com.example.thefinalproject.network.model.user.register

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterRequest (
    val name: String?,
    val email: String?,
    val phoneNumber: String?,
    val password: String?
):Parcelable