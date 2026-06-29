package com.example.skillflow.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillflow.data.dataclass.CourseUI
import com.example.skillflow.data.repository.AppRepository
import com.example.skillflow.ui.mapper.CourseUiMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val repository: AppRepository,
    private val mapper: CourseUiMapper,
) : ViewModel() {

    private val _course = MutableStateFlow<CourseUI?>(null)
    val course: StateFlow<CourseUI?> = _course

    fun loadCourse(courseId: Int) {
        viewModelScope.launch {
            val entity = repository.getCourseById(courseId)
            _course.value = mapper.map(entity)
        }
    }
}