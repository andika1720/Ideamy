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

    fun getFreeCourses(
        categories: List<String>,
        levels: List<String>
    ) = liveData(Dispatchers.IO) {
        try {
            emit(Resource.loading(null))

            val allCourses = repo.getList().data

            val freeCourses = allCourses?.filter { course ->
                course.type == "gratis" &&
                        course.category in categories &&
                        course.level in levels
            } ?: emptyList()

            emit(Resource.success(freeCourses))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!"))
        }
    }

    fun getPremiumCourses(
        categories: List<String>,
        levels: List<String>
    ) = liveData(Dispatchers.IO) {
        try {
            emit(Resource.loading(null))

            val allCourses = repo.getList().data

            val premiumCourses = allCourses?.filter { course ->
                course.type == "premium" &&
                        course.category in categories &&
                        course.level in levels
            } ?: emptyList()

            emit(Resource.success(premiumCourses))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!"))
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
                        course.type == type
            } ?: emptyList()

            // Jika kategori dan level kosong, tampilkan semua yang termasuk pada type nya
            if (categories.isNullOrEmpty() && levels.isNullOrEmpty()) {
                val allTypeCourses = allCourses?.filter { it.type == type } ?: emptyList()
                emit(Resource.success(allTypeCourses))
            } else {
                emit(Resource.success(filteredCourses))
            }
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!"))
        }
    }
}