package com.mohsenoid.rickandmorty

import androidx.multidex.MultiDexApplication
import com.mohsenoid.rickandmorty.data.dataModule
import com.mohsenoid.rickandmorty.injection.appModule
import com.mohsenoid.rickandmorty.injection.qualifier.QualifiersNames
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import timber.log.Timber

class RickAndMortyApplication : MultiDexApplication(), KoinComponent {

    private val isDebug: Boolean = BuildConfig.DEBUG

    override fun onCreate() {
        super.onCreate()

        if (isDebug) setupTimber()

        startKoin {
            val appProperties: Map<String, String> = mapOf(
                QualifiersNames.BASE_URL to BuildConfig.BASE_URL
            )
            properties(appProperties)

            if (isDebug) androidLogger()

            androidContext(this@RickAndMortyApplication)
            modules(appModule + dataModule)
        }
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
