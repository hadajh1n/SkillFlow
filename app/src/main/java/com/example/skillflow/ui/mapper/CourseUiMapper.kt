package com.example.skillflow.ui.mapper

import com.example.skillflow.data.dataclass.CourseUI
import com.example.skillflow.data.room.CourseEntity

class CourseUiMapper {

    fun map(entity: CourseEntity): CourseUI =
        CourseUI(
            id = entity.id,
            title = entity.title,
            text = entity.text,
            price = "${entity.price} ₽",
            rate = entity.rate,
            startDate = entity.startDate,
            hasLike = entity.hasLike,
            publishDate = entity.publishDate,
        )
}