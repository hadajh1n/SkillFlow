package com.example.skillflow.di

import com.example.skillflow.ui.viewModel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { LoginViewModel(get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { FavoritesViewModel(get(), get()) }
    viewModel { DetailsViewModel(get(), get()) }

}