package com.example.skillflow.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skillflow.data.local.SortOrder
import com.example.skillflow.data.repository.AppRepository
import com.example.skillflow.ui.mapper.CourseUiMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val mapper = CourseUiMapper()

    val sortOrder = MutableStateFlow(SortOrder.DESC)

    fun toggleSort() {
        sortOrder.value =
            if (sortOrder.value == SortOrder.DESC) SortOrder.ASC
            else SortOrder.DESC
    }

    val courses = AppRepository.getAllCourses()
        .combine(sortOrder) { entities, order ->
            val sorted = when (order) {
                SortOrder.ASC -> entities.sortedBy { it.publishDate }
                SortOrder.DESC -> entities.sortedByDescending { it.publishDate }
            }

            sorted.map { mapper.map(it) }
        }
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