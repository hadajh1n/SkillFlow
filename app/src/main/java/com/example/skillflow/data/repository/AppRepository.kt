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
            "voice-note-db"
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

    suspend fun getAllCourses(): List<CourseEntity> = courseDao.getAllCourses()
}