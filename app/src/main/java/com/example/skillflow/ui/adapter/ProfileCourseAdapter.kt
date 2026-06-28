package com.example.skillflow.ui.adapter

import com.example.skillflow.R
import com.example.skillflow.data.dataclass.CourseUI
import com.example.skillflow.databinding.ItemCourseProfileBinding
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun profileCourseAdapter() =
    adapterDelegateViewBinding<CourseUI, CourseUI, ItemCourseProfileBinding>(
        { layoutInflater, root ->
            ItemCourseProfileBinding.inflate(layoutInflater, root, false)
        }
    ) {
        bind {
            with(binding) {
                imItemCourseProfile.setImageResource(getCourseImageRes(item.id))
                tvTitleItemCourse.text = item.title
                tvRate.text = item.rate
                tvDateCourse.text = item.startDate
                tvProgressPercent.text = "${item.percent}%"
                tvLessonsCurrent.text = item.lessonsCompleted.toString()
                tvLessonsAll.text = "${item.lessonsTotal} уроков"
                progressCourse1.progress = item.progress
            }
        }
    }

private fun getCourseImageRes(courseId: Int): Int = when (courseId) {
    100 -> R.drawable.img_java
    101 -> R.drawable.img_3d
    102 -> R.drawable.img_python
    103 -> R.drawable.img_java
    104 -> R.drawable.img_java
    else -> R.drawable.img_java
}