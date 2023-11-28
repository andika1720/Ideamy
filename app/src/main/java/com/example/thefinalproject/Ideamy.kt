package com.example.thefinalproject

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Ideamy : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Ideamy)
            modules(
                listOf(

                )
            )
        }
    }
}