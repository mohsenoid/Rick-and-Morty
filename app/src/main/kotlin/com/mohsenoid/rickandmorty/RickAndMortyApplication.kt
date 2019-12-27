package com.mohsenoid.rickandmorty

import android.app.Application
import com.mohsenoid.rickandmorty.injection.AndroidModule
import com.mohsenoid.rickandmorty.injection.qualifier.QualifiersNames
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class RickAndMortyApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @JvmField
    @field:[Inject Named(QualifiersNames.IS_DEBUG)]
    var isDebug: Boolean = false

    private val component: RickAndMortyApplicationComponent by lazy {
        DaggerRickAndMortyApplicationComponent
            .builder()
            .androidModule(AndroidModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        component.inject(this)

        if (isDebug) setupTimber()
    }

    private fun setupTimber() {
        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String {
                // adding file name and line number link to logs
                return "${super.createStackElementTag(element)}(${element.fileName}:${element.lineNumber})"
            }
        })
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }
}
