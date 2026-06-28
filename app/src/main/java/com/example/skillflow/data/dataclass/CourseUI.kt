package com.example.skillflow.data.dataclass

data class CourseUI(
    val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: String,
    val startDate: String,
    val hasLike: Boolean,
    val publishDate: String,

    // Заглушка profile
    val percent: Int = 0,
    val progress: Int = 0,
    val lessonsCompleted: Int = 0,
    val lessonsTotal: Int = 0
)