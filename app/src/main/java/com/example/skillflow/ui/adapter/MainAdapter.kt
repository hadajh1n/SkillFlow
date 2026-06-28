package com.example.skillflow.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.skillflow.core.utils.Constants.PAYLOAD_FAVORITE
import com.example.skillflow.data.dataclass.CourseUI
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class MainAdapter(
    onFavoriteClick: (CourseUI) -> Unit,
    onDetailsClick: (CourseUI) -> Unit,
) : AsyncListDifferDelegationAdapter<CourseUI>(CourseDiffCallback()) {

    init {
        delegatesManager.addDelegate(
            courseAdapter(onFavoriteClick, onDetailsClick)
        )
    }

    class CourseDiffCallback : DiffUtil.ItemCallback<CourseUI>() {

        override fun areItemsTheSame(oldItem: CourseUI, newItem: CourseUI): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CourseUI, newItem: CourseUI): Boolean {
            return oldItem == newItem
        }

        override fun getChangePayload(oldItem: CourseUI, newItem: CourseUI): Any? {
            if (oldItem.hasLike != newItem.hasLike) {
                return PAYLOAD_FAVORITE
            }

            return null
        }
    }
}