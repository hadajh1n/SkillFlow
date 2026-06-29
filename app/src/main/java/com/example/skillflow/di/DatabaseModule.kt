package com.example.skillflow.di

import androidx.room.Room
import com.example.skillflow.data.room.AppDatabase
import org.koin.dsl.module

val databaseModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "skillflow-db"
        ).build()
    }

    single { get<AppDatabase>().courseDao() }
}