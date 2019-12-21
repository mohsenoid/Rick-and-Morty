package com.mohsenoid.rickandmorty

import android.app.Application
import com.mohsenoid.rickandmorty.injection.DependenciesProvider

class RickAndMortyApplication : Application() {

    lateinit var dependenciesProvider: DependenciesProvider
        private set

    override fun onCreate() {
        super.onCreate()
        dependenciesProvider = DependenciesProvider(this)
    }
}
