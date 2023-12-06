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

    fun getAllList() = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(repo.getList()))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!"))
        }
    }

    fun getDataById(id: String) = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(repo.getDataByid(id)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!"))
        }
    }

    fun getFilter(type: String, category:String, level: String) = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(repo.getFilter(type, category, level)))
        } catch (exception: Exception) {
            emit(Resource.error(null,exception.message ?: "Error Occurred!"))
        }
    }

    //percobaan
    fun getFilterCourse(id: String?,category: String?,level: String?, type: String?) = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(repo.getFilter2(id, category, level, type)))
        } catch (exception : Exception) {
            emit(Resource.error(data = null,exception.message?: "Error Occurred!"))
        }
    }

    fun getFilteredCourses(
        position: Int,
        categories: List<String>?,
        levels: List<String>?
    ) = liveData(Dispatchers.IO) {
        try {
            emit(Resource.loading(null))
            val type = when (position) {
                0 -> "all"
                1 -> "premium"
                else -> "free"
            }

            val allCourses = repo.getList().data

            val filteredCourses = allCourses?.filter { course ->
                (categories == null || categories.isEmpty() || course.category in categories) &&
                        (levels == null || levels.isEmpty() || course.level in levels) &&
                        (type == null || course.type == type)
            } ?: emptyList()

            if (type.isNullOrEmpty()) {
                emit(Resource.success(allCourses))
            } else {
                // Jika kategori dan level kosong, tampilkan semua yang termasuk pada type nya
                if (categories.isNullOrEmpty() && levels.isNullOrEmpty()) {
                    val allTypeCourses = allCourses?.filter { it.type == type } ?: emptyList()
                    emit(Resource.success(allTypeCourses))
                } else {
                    emit(Resource.success(filteredCourses))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!"))
        }
    }


}