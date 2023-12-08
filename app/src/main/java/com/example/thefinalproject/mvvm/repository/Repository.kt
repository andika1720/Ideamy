package com.example.thefinalproject.mvvm.repository

import androidx.lifecycle.liveData
import com.example.thefinalproject.network.api.ApiService
import com.example.thefinalproject.network.model.user.login.LoginRequest
import com.example.thefinalproject.network.model.user.register.RegisterRequest
import com.example.thefinalproject.util.Resource
import kotlinx.coroutines.Dispatchers

class Repository(private val apiService: ApiService) {

    suspend fun loginUser(email: String,phoneNumber: String, encryptedPassword: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val user = LoginRequest(email, phoneNumber,encryptedPassword)
            emit(Resource.success(apiService.loginUser(user)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    suspend fun regisUser(name:String,email: String,phoneNumber: String, encryptedPassword: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val user = RegisterRequest(name,email, phoneNumber,encryptedPassword)
            emit(Resource.success(apiService.registerUser(user)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }
    suspend fun getCategory(category: String?) = apiService.getDataByCategory(category)

    suspend fun getList() = apiService.getlist()

    suspend fun getDataByid(id: String)= apiService.getDataById(id)

   suspend fun getFilter(type:String?,category:String?,level:String?) = apiService.getFilteredCourses(type,category,level)
   suspend fun getFilter2(id: String?,category: String?,level: String?, type: String?, search: String?) = apiService.getFilterCourse(id,level,category,type,search)


}