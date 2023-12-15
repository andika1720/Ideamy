package com.example.thefinalproject.mvvm.repository


import com.example.thefinalproject.network.api.ApiService
import com.example.thefinalproject.network.model.user.login.LoginRequest
import com.example.thefinalproject.network.model.user.otp.OtpRequest
import com.example.thefinalproject.network.model.user.register.RegisterRequest


class Repository(private val apiService: ApiService) {

    suspend fun login(loginRequest: LoginRequest) = apiService.loginUser(loginRequest)

    suspend fun register(registerRequest: RegisterRequest) = apiService.registerUser(registerRequest)

    suspend fun getCurrentUser(token: String) = apiService.getCurrentUser(token)
    suspend fun getCategory(category: String?) = apiService.getDataByCategory(category)

    suspend fun getList() = apiService.getlist()

    suspend fun getDataByid(id: String)= apiService.getDataById(id)
    suspend fun getCoursesByTitle(title:String) = apiService.getCourseByTitle(title)
   suspend fun getFilter(type:String?,category:String?,level:String?) = apiService.getFilteredCourses(type,category,level)
   suspend fun getFilter2(id: String?,category: String?,level: String?, type: String?, search: String?) = apiService.getFilterCourse(id,level,category,type,search)

    suspend fun checkOtp(otpRequest: OtpRequest) = apiService.checkOtp(otpRequest)


}