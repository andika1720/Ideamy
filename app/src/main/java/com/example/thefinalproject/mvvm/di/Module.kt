package com.example.thefinalproject.mvvm.di

import com.example.thefinalproject.mvvm.repository.Repository
import com.example.thefinalproject.mvvm.viewmmodel.ViewModelAll
import com.example.thefinalproject.network.api.ApiClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object Module {

    val moduleData
        get() = module {

            single { ApiClient.instance }

            factory { Repository(get()) }
        }


    val uiModule
        get() = module {
            viewModel { ViewModelAll(get())}
        }
}