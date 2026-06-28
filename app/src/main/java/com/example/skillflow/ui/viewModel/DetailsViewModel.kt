package com.example.skillflow.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillflow.data.dataclass.CourseUI
import com.example.skillflow.data.repository.AppRepository
import com.example.skillflow.ui.mapper.CourseUiMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {

    private val mapper = CourseUiMapper()

    private val _course = MutableStateFlow<CourseUI?>(null)
    val course: StateFlow<CourseUI?> = _course

    fun loadCourse(courseId: Int) {
        viewModelScope.launch {
            val entity = AppRepository.getCourseById(courseId)
            _course.value = mapper.map(entity)
        }
    }
}