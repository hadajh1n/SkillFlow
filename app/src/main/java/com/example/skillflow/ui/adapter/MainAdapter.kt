package com.example.skillflow.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.skillflow.data.dataclass.CourseUI
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class MainAdapter(
    onItemClick: (CourseUI) -> Unit,
    onFavoriteClick: (CourseUI) -> Unit
) : AsyncListDifferDelegationAdapter<CourseUI>(CourseDiffCallback()) {

    init {
        delegatesManager.addDelegate(courseAdapter(onItemClick, onFavoriteClick))
    }

    class CourseDiffCallback : DiffUtil.ItemCallback<CourseUI>() {

        override fun areItemsTheSame(oldItem: CourseUI, newItem: CourseUI): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CourseUI, newItem: CourseUI): Boolean {
            return oldItem == newItem
        }
    }
}