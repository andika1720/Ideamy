package com.example.thefinalproject.network.model.user.updateprofile



data class ReqNewUser(
    val address: String?,
    val email: String?,
    val encryptedPassword: String?,
    val image: String?,
    val name: String?,
    val phoneNumber: String?,

)