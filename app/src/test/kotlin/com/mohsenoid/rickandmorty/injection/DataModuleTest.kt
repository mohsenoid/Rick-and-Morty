package com.mohsenoid.rickandmorty.injection

import android.os.Build
import com.mohsenoid.rickandmorty.data.network.NetworkConstants
import com.mohsenoid.rickandmorty.injection.qualifier.QualifiersNames
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.test.check.checkModules
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class DataModuleTest : ModuleTest() {

    @Test
    fun `check all definitions from dataModule`() {
        startKoin {
            val appProperties: Map<String, String> = mapOf(
                QualifiersNames.BASE_URL to NetworkConstants.BASE_URL
            )
            properties(appProperties)

            androidContext(application)
            modules(appModule + dataModule)
        }.checkModules()
    }
}
