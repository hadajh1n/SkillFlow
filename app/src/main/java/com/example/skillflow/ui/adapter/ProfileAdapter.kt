package com.example.skillflow.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.skillflow.data.dataclass.CourseUI
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class ProfileAdapter : AsyncListDifferDelegationAdapter<CourseUI>(CourseDiffCallback()) {

    init {
        delegatesManager.addDelegate(profileCourseAdapter())
    }

    class CourseDiffCallback : DiffUtil.ItemCallback<CourseUI>() {
        override fun areItemsTheSame(oldItem: CourseUI, newItem: CourseUI): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CourseUI, newItem: CourseUI): Boolean =
            oldItem == newItem
    }
}