package com.mohsenoid.rickandmorty

import androidx.multidex.MultiDexApplication
import com.mohsenoid.rickandmorty.data.dataModule
import com.mohsenoid.rickandmorty.util.KoinQualifiersNames
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

class RickAndMortyApplication : MultiDexApplication(), KoinComponent {

    private val isDebug: Boolean = BuildConfig.DEBUG

    override fun onCreate() {
        super.onCreate()

        startKoin {
            val appProperties: Map<String, String> = mapOf(
                KoinQualifiersNames.BASE_URL to BuildConfig.BASE_URL,
            )
            properties(appProperties)

            if (isDebug) androidLogger()

            androidContext(this@RickAndMortyApplication)
            modules(appModule + dataModule)
        }
    }
}
