package com.example.skillflow.ui.fragment.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skillflow.MainTabsGraphDirections
import com.example.skillflow.databinding.FragmentHomeBinding
import com.example.skillflow.ui.adapter.MainAdapter
import com.example.skillflow.ui.viewModel.HomeViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModel()

    private val courseAdapter = MainAdapter(
        onFavoriteClick = { course ->
            viewModel.toggleFavorite(course.id)
        },
        onDetailsClick = { course ->
            findNavController().navigate(MainTabsGraphDirections.actionGlobalDetails(course.id))
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
        setupSort()
    }

    override fun onDestroyView() = with(binding) {
        super.onDestroyView()
        rvCourses.adapter = null
        _binding = null
    }

    private fun setupRecyclerView() {
        binding.rvCourses.apply {
            adapter = courseAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeViewModel() = with(binding) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.courses.collect { list ->
                    courseAdapter.items = list
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sortOrder.collect { order ->
                    tvSort.text = order.label
                }
            }
        }
    }

    private fun setupSort() = with(binding) {
        tvSort.setOnClickListener {
            viewModel.toggleSort()
        }
    }
}