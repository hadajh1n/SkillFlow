package com.example.skillflow.data.mapper

import com.example.skillflow.data.dataclass.CourseDTO
import com.example.skillflow.data.room.CourseEntity

class CourseDtoEntityMapper {

    fun fromDtoToEntity(dto: CourseDTO): CourseEntity =
        CourseEntity(
            id = dto.id,
            title = dto.title,
            text = dto.text,
            price = dto.price,
            rate = dto.rate,
            startDate = dto.startDate,
            hasLike = dto.hasLike,
            publishDate = dto.publishDate,
        )
}