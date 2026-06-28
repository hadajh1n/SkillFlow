package com.example.skillflow.data.repository

import androidx.room.Room
import com.example.skillflow.core.app.SkillFlowApp
import com.example.skillflow.data.dataclass.CourseDTO
import com.example.skillflow.data.mapper.CourseDtoEntityMapper
import com.example.skillflow.data.room.AppDatabase
import com.example.skillflow.data.room.CourseEntity
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

object AppRepository {

    private val courseDtoMapper = CourseDtoEntityMapper()

    private val cacheMutex = Mutex()

    private val db by lazy {
        Room.databaseBuilder(
            SkillFlowApp.Companion.instance.applicationContext,
            AppDatabase::class.java,
            "skillflow-db"
        ).build()
    }

    private val courseDao = db.courseDao()

    suspend fun setCourse(
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