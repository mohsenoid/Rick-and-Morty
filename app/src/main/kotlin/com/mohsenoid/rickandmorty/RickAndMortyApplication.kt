package com.mohsenoid.rickandmorty

import android.app.Application
import com.mohsenoid.rickandmorty.injection.DependenciesProvider
import timber.log.Timber

class RickAndMortyApplication : Application() {

    lateinit var dependenciesProvider: DependenciesProvider
        private set

    override fun onCreate() {
        super.onCreate()
        dependenciesProvider = DependenciesProvider(this)

        if (BuildConfig.DEBUG) setupTimber()
    }

    private fun setupTimber() {
        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String {
                // adding file name and line number link to logs
                return "${super.createStackElementTag(element)}(${element.fileName}:${element.lineNumber})"
            }
        })
    }
}
