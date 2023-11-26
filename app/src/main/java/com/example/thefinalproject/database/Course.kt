package com.example.thefinalproject.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "")
data class Course(
    @PrimaryKey(autoGenerate = true)
    var id: Int= 0
)
