package com.mohsenoid.rickandmorty.startup

import android.content.Context
import androidx.startup.Initializer
import com.mohsenoid.rickandmorty.BuildConfig
import timber.log.Timber

class TimberInitializer : Initializer<Unit> {

    private val isDebug: Boolean = BuildConfig.DEBUG

    override fun create(context: Context) {
        if (isDebug) setupTimber()
    }

    private fun setupTimber() {
        Timber.plant(
            object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    // adding file name and line number link to logs
                    return "${super.createStackElementTag(element)}(${element.fileName}:${element.lineNumber})"
                }
            },
        )
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}
