package com.mohsenoid.rickandmorty

import android.app.Application
import com.mohsenoid.rickandmorty.data.network.NetworkConstants
import com.mohsenoid.rickandmorty.injection.appModule
import com.mohsenoid.rickandmorty.injection.dataModule
import com.mohsenoid.rickandmorty.injection.qualifier.QualifiersNames
import com.mohsenoid.rickandmorty.view.character.details.characterDetailsFragmentModule
import com.mohsenoid.rickandmorty.view.character.list.characterListFragmentModule
import com.mohsenoid.rickandmorty.view.episode.list.episodeListFragmentModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import timber.log.Timber
import java.util.HashMap

class RickAndMortyApplication : Application(), KoinComponent {

    private val isDebug: Boolean = BuildConfig.DEBUG

    override fun onCreate() {
        super.onCreate()

        if (isDebug) setupTimber()

        startKoin {
            val appProperties: HashMap<String, Any> = hashMapOf(
                QualifiersNames.IS_DEBUG to isDebug,
                QualifiersNames.BASE_URL to NetworkConstants.BASE_URL
            )
            properties(appProperties)

            if (isDebug) androidLogger()

            androidContext(this@RickAndMortyApplication)
            modules(appModule + dataModule + episodeListFragmentModule + characterListFragmentModule + characterDetailsFragmentModule)
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
