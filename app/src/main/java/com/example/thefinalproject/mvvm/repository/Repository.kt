package com.example.thefinalproject.mvvm.repository

import com.example.thefinalproject.network.api.ApiService

class Repository(private val apiService: ApiService) {

    suspend fun getCategory(category: String?) = apiService.getDataByCategory(category)

    suspend fun getList() = apiService.getlist()

    suspend fun getDataByid(id: String)= apiService.getDataById(id)

   suspend fun getFilter(type:String?,category:String?,level:String?) = apiService.getFilteredCourses(type,category,level)
   suspend fun getFilter2(id: String?,category: String?,level: String?, type: String?) = apiService.getFilterCourse(id,category,level,type)


}