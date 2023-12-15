package com.example.thefinalproject.util

import com.example.thefinalproject.network.model.course.ChapterById
import com.example.thefinalproject.network.model.course.ModuleById

sealed class CourseDetailItem{
    data class ChapterItem(val chapter: ChapterById) : CourseDetailItem()
    data class ModuleItem(val module: ModuleById) : CourseDetailItem()
}
