package com.example.skillflow.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillflow.data.repository.AppRepository
import com.example.skillflow.ui.mapper.CourseUiMapper
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel : ViewModel() {

    private val mapper = CourseUiMapper()

    val coursesFavorites = AppRepository.getAllCoursesFavorites()
        .map { entities -> entities.map { mapper.map(it) } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun toggleFavorite(courseId: Int) {
        viewModelScope.launch {
            AppRepository.toggleFavorite(courseId)
        }
    }
}