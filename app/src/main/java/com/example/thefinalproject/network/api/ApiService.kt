package com.example.thefinalproject.network.api


import com.example.thefinalproject.network.model.CategoryResponse
import com.example.thefinalproject.network.model.ListResponse
import retrofit2.http.GET

interface ApiService {

    @GET("/api/v1/course")
    suspend fun getlist():ListResponse

    @GET("/api/v1/course")
    suspend fun getCategory():CategoryResponse
}