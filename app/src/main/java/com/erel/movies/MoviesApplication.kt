package com.erel.movies

import android.app.Application
import com.erel.movies.data.DataModule
import com.erel.movies.domain.injection.DomainModule
import com.erel.movies.injection.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MoviesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MoviesApplication)
            modules(
                listOf(
                    DataModule.create(),
                    DomainModule.create(),
                    PresentationModule.create()
                )
            )
        }
    }
}