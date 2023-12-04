package com.example.thefinalproject.mvvm.viewmmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.thefinalproject.mvvm.repository.Repository
import com.example.thefinalproject.util.Resource
import kotlinx.coroutines.Dispatchers

class ViewModelAll(private val repo: Repository) : ViewModel() {

    fun getAllCategory() = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(repo.getCategory()))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!"))
        }
    }

    fun getAllList() = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(repo.getList()))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!"))
        }
    }

    fun getFilteredCourses(type: String?, category: String?, level: String?) = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(repo.getFilteredCourses(type, category, level)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!"))
        }
    }
}