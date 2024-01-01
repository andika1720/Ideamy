package com.example.thefinalproject.mvvm.viewmmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.thefinalproject.mvvm.repository.Repository
import com.example.thefinalproject.network.model.course.CategoryResponse
import com.example.thefinalproject.network.model.course.DataCategory
import com.example.thefinalproject.network.model.course.ListResponse
import com.example.thefinalproject.network.model.user.forgotpassword.postdata.PostForgotPassRequest
import com.example.thefinalproject.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.ArrayList

class ViewModelAll(private val repo: Repository) : ViewModel() {
    private val _filteredData = MutableLiveData<Resource<CategoryResponse>>()
    val filteredData: LiveData<Resource<CategoryResponse>>
        get() = _filteredData
    fun getAllCategory(category: String?) = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(repo.getCategory(category)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!"))
        }
    }

    fun getFilter(token: String?, rating: Double?, category: String?, level: String?, terbaru: String?) = liveData(Dispatchers.IO) {
        try {
            // Gantilah dengan metode yang sesuai di dalam repo
            val result = repo.getFilter(token, rating, category, level, terbaru)
            emit(Resource.success(result))
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
    fun getFilterCourse(token: String?,id: String?,level: String?, category: String?,type: String?,search: String?) = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(repo.getFilter2(token,id, level,category, type, search)))
        } catch (exception : Exception) {
            emit(Resource.error(data = null,exception.message?: "Error Occurred!"))
        }
    }

    fun getFilterInCourse(category: String?,level: String?, createdAt:String?, rating:String) = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(repo.getFiltersCourse(category,level, createdAt, rating)))
        } catch (exception : Exception) {
            emit(Resource.error(data = null,exception.message?: "Error Occurred!"))
        }
    }
    fun getChapters(courseId: String?) = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(repo.getChapters(courseId)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!"))
        }
    }

    fun getModules(chapterId: String?) = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(repo.getModules(chapterId)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!"))
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