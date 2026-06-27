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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skillflow.databinding.FragmentFavoritesBinding
import com.example.skillflow.ui.adapter.MainAdapter
import com.example.skillflow.ui.viewModel.FavoritesViewModel
import kotlinx.coroutines.launch
import kotlin.getValue

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoritesViewModel by viewModels()

    private val courseAdapter = MainAdapter(
        onItemClick = { course ->

        },
        onFavoriteClick = { course ->
            viewModel.toggleFavorite(course.id)
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
    }

    override fun onDestroyView() = with(binding) {
        super.onDestroyView()
        rvFavotitesCourses.adapter = null
        _binding = null
    }

    private fun setupRecyclerView() {
        binding.rvFavotitesCourses.apply {
            adapter = courseAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.coursesFavorites.collect { list ->
                    courseAdapter.items = list
                }
            }
        }
    }
}