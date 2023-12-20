package com.example.thefinalproject.mvvm.repository


import com.example.thefinalproject.network.api.ApiService
import com.example.thefinalproject.network.model.user.login.LoginRequest
import com.example.thefinalproject.network.model.user.otp.OtpRequest
import com.example.thefinalproject.network.model.user.otp.resendotp.ResendOtpRequest
import com.example.thefinalproject.network.model.user.register.RegisterRequest


class Repository(private val apiService: ApiService) {

    suspend fun login(loginRequest: LoginRequest) = apiService.loginUser(loginRequest)

    suspend fun register(registerRequest: RegisterRequest) = apiService.registerUser(registerRequest)

    suspend fun getCurrentUser(token: String?) = apiService.currentUser(token)
    suspend fun getCategory(category: String?) = apiService.getDataByCategory(category)



    suspend fun getDataByid(token: String?,id: String)= apiService.getDataById(token,id)

    suspend fun getDataByid1(id: String)= apiService.getDataById1(id)
    suspend fun getCoursesByTitle(title:String) = apiService.getCourseByTitle(title)

   suspend fun getFilter2(id: String?,category: String?,level: String?, type: String?, search: String?) = apiService.getFilterCourse(id,level,category,type,search)

    suspend fun checkOtp(otpRequest: OtpRequest) = apiService.checkOtp(otpRequest)

    suspend fun resendOtp(resendOtpRequest: ResendOtpRequest) = apiService.resendOtp(resendOtpRequest)


}