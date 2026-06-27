package com.example.skillflow.ui.adapter

import com.example.skillflow.R
import com.example.skillflow.data.dataclass.CourseUI
import com.example.skillflow.databinding.ItemCourseHomeAndFavoritesBinding
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun courseAdapterDelegate(
    onItemClick: (CourseUI) -> Unit,
    onFavoriteClick: (CourseUI) -> Unit
) = adapterDelegateViewBinding<CourseUI, CourseUI, ItemCourseHomeAndFavoritesBinding>(
    { layoutInflater, root -> ItemCourseHomeAndFavoritesBinding.inflate(layoutInflater, root, false) }
) {
    binding.root.setOnClickListener { onItemClick(item) }

    binding.imAddFavorites.setOnClickListener { onFavoriteClick(item) }

    bind {
        with(binding) {
            tvTitleItemCourse.text = item.title
            tvItemCourseDescription.text = item.text
            tvItemCourseCost.text = "${item.price} ₽"
            tvRate.text = item.rate
            tvDateCourse.text = item.startDate

            val favIcon = if (item.hasLike) {
                R.drawable.icon_favorites_added
            } else {
                R.drawable.icon_favorites
            }
            imAddFavorites.setImageResource(favIcon)
        }
    }
}