package com.example.thefinalproject.network.api


import com.example.thefinalproject.network.model.course.CategoryResponse
import com.example.thefinalproject.network.model.course.DetailResponse
import com.example.thefinalproject.network.model.course.ListResponse
import com.example.thefinalproject.network.model.user.getuser.GetCurrentUser
import com.example.thefinalproject.network.model.user.login.LoginRequest
import com.example.thefinalproject.network.model.user.login.LoginResponse
import com.example.thefinalproject.network.model.user.otp.OtpRequest
import com.example.thefinalproject.network.model.user.otp.OtpResponse
import com.example.thefinalproject.network.model.user.register.RegisterRequest
import com.example.thefinalproject.network.model.user.register.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("courses")
    suspend fun getlist(): ListResponse


    @GET("courses")
    suspend fun getDataByCategory(
        @Query("category") category: String?): CategoryResponse
    @GET("courses/{id}")
    suspend fun getDataById(
        @Header("Authorization") token : String?,
        @Path("id") id: String
    ): DetailResponse

    @GET("courses")
    suspend fun getFilteredCourses(
        @Query("type") type: String?,
        @Query("category") category: String?,
        @Query("level") level: String?
    ): CategoryResponse

    @GET("courses")
    suspend fun getCourseByTitle(
        @Query("title") title: String?
    ) : CategoryResponse
    @GET("courses")
    suspend fun getFilterCourse(
        @Query("id") id: String?,
        @Query("category") category: String?,
        @Query("level") level: String?,
        @Query("type") type: String?,
        @Query("search") search: String?,
    ) : ListResponse

    @POST("login")
    suspend fun loginUser(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @POST("register")
    suspend fun registerUser(
        @Body registerRequest: RegisterRequest
    ) : RegisterResponse

    @POST ("account-verify")
    suspend fun checkOtp(
        @Body otpRequest: OtpRequest
    ): OtpResponse

    @GET ("current-user")
    fun currentUser(
        @Header("Authorization") token : String?
    ): GetCurrentUser
}