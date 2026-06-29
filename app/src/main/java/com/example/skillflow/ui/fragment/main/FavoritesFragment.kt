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
import com.example.skillflow.databinding.FragmentFavoritesBinding
import com.example.skillflow.ui.adapter.MainAdapter
import com.example.skillflow.ui.viewModel.FavoritesViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoritesViewModel by viewModel()

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

    private fun observeViewModel() = with(binding) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.coursesFavorites.collect { list ->
                    courseAdapter.items = list

                    if (list.isEmpty()) {
                        rvFavotitesCourses.visibility = View.GONE
                        llEmptyList.visibility = View.VISIBLE
                    } else {
                        rvFavotitesCourses.visibility = View.VISIBLE
                        llEmptyList.visibility = View.GONE
                    }
                }
            }
        }
    }
}