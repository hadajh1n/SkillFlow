package com.example.skillflow.ui.fragment.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.skillflow.R
import com.example.skillflow.data.dataclass.CourseUI
import com.example.skillflow.databinding.FragmentDetailsBinding
import com.example.skillflow.ui.viewModel.DetailsViewModel
import kotlinx.coroutines.launch

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBackButton()
        observeViewModel()

        viewModel.loadCourse(args.courseId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupBackButton() = with(binding) {
        imBackDetails.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.course.collect { course ->
                    course?.let { bindUI(it) }
                }
            }
        }
    }

    private fun bindUI(course: CourseUI) = with(binding) {
        imCourseDetails.setImageResource(getCourseImageRes(course.id))
        tvRate.text = course.rate
        tvDateCourse.text = course.startDate
        tvTitleCourseDetails.text = course.title
    }

    private fun getCourseImageRes(courseId: Int): Int = when (courseId) {
        100 -> R.drawable.img
        101 -> R.drawable.img
        102 -> R.drawable.img
        103 -> R.drawable.img
        104 -> R.drawable.img
        else -> R.drawable.img
    }
}