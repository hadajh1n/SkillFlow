package com.example.skillflow.core.app

import android.app.Application

class SkillFlowApp : Application() {

    companion object {
        lateinit var instance: SkillFlowApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this@SkillFlowApp
    }
}