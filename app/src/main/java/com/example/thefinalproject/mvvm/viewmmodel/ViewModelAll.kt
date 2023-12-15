package com.example.thefinalproject.mvvm.viewmmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.thefinalproject.mvvm.repository.Repository
import com.example.thefinalproject.network.model.course.DataCategory
import com.example.thefinalproject.util.Resource
import kotlinx.coroutines.Dispatchers

class ViewModelAll(private val repo: Repository) : ViewModel() {
    private var searchCourseLiveData = MutableLiveData<List<DataCategory>>()

    fun getAllCategory(category: String?) = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(repo.getCategory(category)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!"))
        }
    }


    fun getCourseByTitle(title : String) = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(repo.getCoursesByTitle(title)))
        } catch (exception: Exception) {
            emit(Resource.error(null,exception.message ?: "Error Occurred!"))
        }
    }
    fun observeSearchCoursesLiveData(): LiveData<List<DataCategory>> = searchCourseLiveData

    fun getAllList() = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(repo.getList()))
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


    //percobaan
    fun getFilterCourse(id: String?,level: String?, category: String?,type: String?,search: String?) = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(repo.getFilter2(id, level,category, type, search)))
        } catch (exception : Exception) {
            emit(Resource.error(data = null,exception.message?: "Error Occurred!"))
        }
    }




}