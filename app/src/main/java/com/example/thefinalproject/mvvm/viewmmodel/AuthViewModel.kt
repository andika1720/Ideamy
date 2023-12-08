package com.example.thefinalproject.mvvm.viewmmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.thefinalproject.mvvm.repository.Repository
import com.example.thefinalproject.util.SharePref

class AuthViewModel(private val repo: Repository): ViewModel() {

    suspend fun login(email: String, phoneNumber: String, encryptedPassword: String) = repo.loginUser(email, phoneNumber, encryptedPassword)

    suspend fun register(nama:String, email: String, phoneNumber: String, encryptedPassword: String) = repo.regisUser(nama,email, phoneNumber, encryptedPassword)

}