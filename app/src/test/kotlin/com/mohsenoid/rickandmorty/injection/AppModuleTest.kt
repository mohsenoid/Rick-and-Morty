package com.mohsenoid.rickandmorty.injection

import android.os.Build
import com.mohsenoid.rickandmorty.appModule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.test.check.checkModules
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class AppModuleTest : ModuleTest() {

    @Test
    fun `check all definitions from appModule`() {
        startKoin {
            androidContext(application)
            modules(appModule)
        }.checkModules()
    }
}
