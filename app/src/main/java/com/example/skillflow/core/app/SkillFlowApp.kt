package com.example.skillflow.core.app

import android.app.Application
import com.example.skillflow.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SkillFlowApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SkillFlowApp)
            modules(
                networkModule,
                databaseModule,
                repositoryModule,
                viewModelModule,
                mapperModule,
            )
        }
    }
}