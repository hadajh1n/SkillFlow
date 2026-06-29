package com.example.skillflow.di

import com.example.skillflow.data.mapper.CourseDtoEntityMapper
import com.example.skillflow.ui.mapper.CourseUiMapper
import org.koin.dsl.module

val mapperModule = module {
    single { CourseUiMapper() }
    single { CourseDtoEntityMapper() }
}