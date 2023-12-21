package com.example.thefinalproject.network.model.mycourse


import com.google.gson.annotations.SerializedName

data class DataMyCourse(
    @SerializedName("Courses")
    val courses: List<Course?>?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("role")
    val role: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)