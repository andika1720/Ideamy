package com.example.thefinalproject.mvvm.repository

import com.example.thefinalproject.network.api.ApiService

class Repository(private val apiService: ApiService) {

    suspend fun getCategory() = apiService.getCategory()

    suspend fun getList() = apiService.getlist()
}