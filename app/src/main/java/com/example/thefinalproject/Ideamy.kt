package com.example.thefinalproject

import android.app.Application
import com.example.thefinalproject.mvvm.di.Module.moduleData
import com.example.thefinalproject.mvvm.di.Module.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Ideamy : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Ideamy)
            modules(
                listOf(
                    moduleData,
                    uiModule

                )
            )
        }
    }
}