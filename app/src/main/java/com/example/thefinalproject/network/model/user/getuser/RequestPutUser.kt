package com.example.thefinalproject.network.model.user.getuser

data class RequestPutUser(
    val name: String?,
    val email: String?,
    val phoneNumber: String?,
    val image: String?,
    val address: String?
)
