package com.example.thefinalproject.network.model.user.getuser

import com.google.gson.annotations.SerializedName

data class PutResponseUser (
        @SerializedName("status")
        val status: String?,
        @SerializedName("message")
        val message: String?,
        @SerializedName("data")
        val data: List<DataPutUser>?
)