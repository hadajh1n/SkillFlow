package com.example.skillflow.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillflow.data.dataclass.CourseUI
import com.example.skillflow.data.repository.AppRepository
import com.example.skillflow.ui.mapper.CourseUiMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _courses = MutableStateFlow<List<CourseUI>>(emptyList())
    val courses: StateFlow<List<CourseUI>> = _courses.asStateFlow()

    private val mapper = CourseUiMapper()

    init {
        Log.e("ViewModel", "Инициализация курсов (homeviewmodel)")
        loadCourses()
    }

    fun loadCourses() {
        viewModelScope.launch {

            Log.e("ViewModel", "Запрос на получение всех курсов из БД (homeviewmodel)")
            val entities = AppRepository.getAllCourses()
            Log.e("ViewModel", "Маппинг в UI (homeviewmodel)")
            _courses.value = entities.map { mapper.map(it) }
        }
    }
}