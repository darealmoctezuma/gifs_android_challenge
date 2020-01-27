package com.example.gifs.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class GifsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GifsApplication)
            androidLogger()
            modules(listOf(gridViewModelModule, repositoryModule, apiModule, networkModule, useCasesModule))
        }
    }
}
