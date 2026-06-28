package com.example.skillflow.ui.adapter

import com.example.skillflow.R
import com.example.skillflow.data.dataclass.CourseUI
import com.example.skillflow.databinding.ItemCourseHomeAndFavoritesBinding
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun courseAdapter(
    onFavoriteClick: (CourseUI) -> Unit,
    onDetailsClick: (CourseUI) -> Unit,
) = adapterDelegateViewBinding<CourseUI, CourseUI, ItemCourseHomeAndFavoritesBinding>(
    { layoutInflater, root -> ItemCourseHomeAndFavoritesBinding
        .inflate(layoutInflater, root, false) }
) {
    binding.imAddFavorites.setOnClickListener { onFavoriteClick(item) }
    binding.tvItemCourseDetails.setOnClickListener { onDetailsClick(item) }

    bind {
        with(binding) {
            imItemCourse.setImageResource(getCourseImageRes(item.id))
            tvTitleItemCourse.text = item.title
            tvItemCourseDescription.text = item.text
            tvItemCourseCost.text = item.price
            tvRate.text = item.rate
            tvDateCourse.text = item.startDate

            val favIcon = if (item.hasLike) {
                R.drawable.icon_favorites_added
            } else {
                R.drawable.icon_favorites_removed
            }
            imAddFavorites.setImageResource(favIcon)
        }
    }
}

// Так как полей с url в json нет сделал заглушку
private fun getCourseImageRes(courseId: Int): Int = when (courseId) {
    100 -> R.drawable.img
    101 -> R.drawable.img
    102 -> R.drawable.img
    103 -> R.drawable.img
    104 -> R.drawable.img
    else -> R.drawable.img
}