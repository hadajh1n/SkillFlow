package com.example.skillflow.data.dataclass

data class CoursesResponse(
    val courses: List<CourseDTO>
)

data class CourseDTO(
    val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: String,
    val startDate: String,
    val hasLike: Boolean,
    val publishDate: String,
)