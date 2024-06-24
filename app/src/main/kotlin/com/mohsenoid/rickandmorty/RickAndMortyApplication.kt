package com.mohsenoid.rickandmorty

import android.app.Application
import com.mohsenoid.rickandmorty.data.dataModule
import com.mohsenoid.rickandmorty.domain.domainModule
import com.mohsenoid.rickandmorty.ui.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RickAndMortyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@RickAndMortyApplication)
            modules(appModule, dataModule, domainModule, uiModule)
        }
    }
}
