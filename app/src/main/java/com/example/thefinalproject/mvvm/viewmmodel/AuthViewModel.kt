package com.example.thefinalproject.mvvm.viewmmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.thefinalproject.mvvm.repository.Repository
import com.example.thefinalproject.network.model.user.login.LoginRequest
import com.example.thefinalproject.network.model.user.register.RegisterRequest
import com.example.thefinalproject.util.Resource
import kotlinx.coroutines.Dispatchers

class AuthViewModel(private val repo: Repository): ViewModel() {


    suspend fun loginUser(loginRequest: LoginRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {

            emit(Resource.success(repo.login(loginRequest)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    suspend fun regisUser(registerRequest: RegisterRequest) = liveData(
        Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repo.register(registerRequest)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

     fun getCurrentUser(token: String) = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(data = repo.getCurrentUser(token)))
        } catch (exception: Exception){
            emit(Resource.error(null, exception.message ?: "Error Occurred!"))
        }
    }
}
