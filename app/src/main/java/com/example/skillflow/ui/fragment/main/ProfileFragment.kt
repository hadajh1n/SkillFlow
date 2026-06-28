package com.example.skillflow.ui.fragment.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skillflow.data.dataclass.CourseUI
import com.example.skillflow.databinding.FragmentProfileBinding
import com.example.skillflow.ui.adapter.ProfileAdapter

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val profileAdapter = ProfileAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        loadStubData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        binding.rvProfileCourses.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = profileAdapter
        }
    }

    private fun loadStubData() {
        val stubCourses = listOf(
            CourseUI(
                id = 101,
                title = "3D-дженералист",
                text = "",
                price = "",
                rate = "3.9",
                startDate = "10 Сентября 2024",
                hasLike = true,
                publishDate = "",
                percent = 50,
                progress = 100,
                lessonsCompleted = 22,
                lessonsTotal = 44,
            ),
            CourseUI(
                id = 100,
                title = "Java-разработчик с нуля",
                text = "",
                price = "",
                rate = "4.9",
                startDate = "29 Мая 2024",
                hasLike = false,
                publishDate = "",
                percent = 30,
                progress = 60,
                lessonsCompleted = 15,
                lessonsTotal = 48,
            )
        )

        profileAdapter.items = stubCourses.toMutableList()
        profileAdapter.notifyDataSetChanged()
    }
}