package com.example.thefinalproject.mvvm.viewmmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData

import com.example.thefinalproject.mvvm.repository.Repository

import com.example.thefinalproject.util.Resource
import kotlinx.coroutines.Dispatchers


class ViewModelAll(private val repo: Repository) : ViewModel() {

    fun getAllCategory(category: String?) = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(repo.getCategory(category)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!"))
        }
    }






    fun getDataById(token: String?,id: String) = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(repo.getDataByid(token,id)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!"))
        }
    }


    fun getDataById1(token: String?,id: String) = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(repo.getDataByid1(token,id)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!"))
        }
    }

    fun getChapterById(token: String?,id: String) = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(repo.getChapterById(token,id)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!"))
        }
    }

    fun getModulesById(token: String?,id: String) = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(repo.getModulesById(token,id)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!"))
        }
    }
    //percobaan
    fun getFilterCourse(token: String?,rating: Double?,level: String?, category: String?,type: String?,search: String?) = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(repo.getFilter2(token,rating, level,category, type, search)))
        } catch (exception : Exception) {
            emit(Resource.error(data = null,exception.message?: "Error Occurred!"))
        }
    }

    fun getAllNotification(token: String?) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repo.getNotification(token)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }

    }


}