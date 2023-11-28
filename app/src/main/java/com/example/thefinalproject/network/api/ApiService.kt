package com.example.thefinalproject.network.api


import com.example.thefinalproject.network.model.ListResponse
import retrofit2.http.GET

interface ApiService {

    @GET()
    suspend fun getlist():ListResponse

}