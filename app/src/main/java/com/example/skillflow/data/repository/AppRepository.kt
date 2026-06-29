package com.example.skillflow.data.repository

import com.example.skillflow.data.dataclass.CourseDTO
import com.example.skillflow.data.mapper.CourseDtoEntityMapper
import com.example.skillflow.data.room.CourseDao
import com.example.skillflow.data.room.CourseEntity
import com.example.skillflow.network.CourseApi
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class AppRepository(
    private val courseDao: CourseDao,
    private val courseDtoMapper: CourseDtoEntityMapper,
    private val courseApi: CourseApi,
) {
    private val cacheMutex = Mutex()

    suspend fun loadCourses(): Result<Unit> {
        return try {
            val response = courseApi.getCourses()
            setCourse(response.courses)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun setCourse(
        dto: List<CourseDTO>
    ) = cacheMutex.withLock {

        dto.forEach { dto ->
            val entity = courseDtoMapper.fromDtoToEntity(dto)
            courseDao.insertCourse(entity)
        }
    }

    fun getAllCourses() = courseDao.getAllCourses()
    fun getAllCoursesFavorites() = courseDao.getAllCoursesFavorites()

    suspend fun toggleFavorite(courseId: Int) {
        courseDao.toggleFavorite(courseId)
    }

    suspend fun getCourseById(id: Int): CourseEntity {
        return courseDao.getCourseById(id)
    }
}