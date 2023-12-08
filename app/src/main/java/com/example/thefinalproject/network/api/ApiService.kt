package com.example.thefinalproject.network.api


import com.example.thefinalproject.network.model.CategoryResponse
import com.example.thefinalproject.network.model.DataCategory
import com.example.thefinalproject.network.model.DetailResponse
import com.example.thefinalproject.network.model.ListResponse
import com.example.thefinalproject.network.model.user.login.LoginRequest
import com.example.thefinalproject.network.model.user.login.LoginResponse
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
    suspend fun getlist():ListResponse


    @GET("course")
    suspend fun getDataByCategory(
        @Query("category") category: String?):CategoryResponse
    @GET("course/{id}")
    suspend fun getDataById(@Path("id") id: String): DetailResponse

    @GET("course")
    suspend fun getFilteredCourses(
        @Query("type") type: String?,
        @Query("category") category: String?,
        @Query("level") level: String?
    ): CategoryResponse

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
    ): Call<LoginResponse>

    @POST("register")
    fun registerUser(
        @Body registerRequest: RegisterRequest
    ) : Call<RegisterResponse>

}