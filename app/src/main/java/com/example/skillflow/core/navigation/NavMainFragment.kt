package com.example.skillflow.core.navigation

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.skillflow.R
import com.example.skillflow.databinding.FragmentMainBinding

fun Fragment.setupBottomNavigation(binding: FragmentMainBinding) = with(binding) {

    val navHostFragment = childFragmentManager
        .findFragmentById(R.id.nav_host_main_fragment) as? NavHostFragment
        ?: return@with

    val navController = navHostFragment.navController

    bottomNavigation.setupWithNavController(navController)
}