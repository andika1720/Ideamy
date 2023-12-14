package com.example.thefinalproject.network.api


import com.example.thefinalproject.network.model.course.CategoryResponse
import com.example.thefinalproject.network.model.course.DetailResponse
import com.example.thefinalproject.network.model.course.ListResponse
import com.example.thefinalproject.network.model.user.login.LoginRequest
import com.example.thefinalproject.network.model.user.login.LoginResponse
import com.example.thefinalproject.network.model.user.otp.OtpRequest
import com.example.thefinalproject.network.model.user.otp.OtpResponse
import com.example.thefinalproject.network.model.user.register.RegisterRequest
import com.example.thefinalproject.network.model.user.register.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("course")
    suspend fun getlist(): ListResponse


    @GET("course")
    suspend fun getDataByCategory(
        @Query("category") category: String?): CategoryResponse
    @GET("course/{id}")
    suspend fun getDataById(@Path("id") id: String): DetailResponse

    @GET("course")
    suspend fun getFilteredCourses(
        @Query("type") type: String?,
        @Query("category") category: String?,
        @Query("level") level: String?
    ): CategoryResponse

    @GET("course")
    suspend fun getCourseByTitle(
        @Query("title") title: String?
    ) : CategoryResponse
    @GET("course")
    suspend fun getFilterCourse(
        @Query("id") id: String?,
        @Query("category") category: String?,
        @Query("level") level: String?,
        @Query("type") type: String?,
        @Query("search") search: String?,
    ) : ListResponse

    @POST("login")
    fun loginUser(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @POST("register")
    fun registerUser(
        @Body registerRequest: RegisterRequest
    ) : Call<RegisterResponse>

    @POST ("account-verify")
    fun checkOtp(
        @Body otpRequest: OtpRequest
    ): Call<OtpResponse>

}