package com.example.thefinalproject.mvvm.viewmmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.thefinalproject.mvvm.repository.Repository
import com.example.thefinalproject.network.model.course.DetailResponse
import com.example.thefinalproject.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val repo: Repository) : ViewModel() {

    private var _courseById: MutableLiveData<Resource<DetailResponse>> = MutableLiveData()
    val courseById: LiveData<Resource<DetailResponse>> get() = _courseById



    fun getCourseById(id: String) = viewModelScope.launch {
        getDataById1(null,id)
    }
    suspend fun getDataById1(token: String?,id: String) {
        try {
            val responses = repo.getDataByid(token, id)
            _courseById.value = Resource.success(responses)
        } catch (exception: Exception) {
            _courseById.value = (Resource.error(null, exception.message ?: "Error Occurred!"))
        }
    }
}