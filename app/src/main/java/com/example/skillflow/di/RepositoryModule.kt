package com.example.skillflow.di

import com.example.skillflow.data.repository.AppRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { AppRepository(get(), get(), get()) }
}