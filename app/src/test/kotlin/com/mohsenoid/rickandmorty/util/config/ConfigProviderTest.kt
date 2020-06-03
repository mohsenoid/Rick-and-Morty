package com.mohsenoid.rickandmorty.util.config

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.annotation.Implements
import org.robolectric.shadows.ShadowConnectivityManager
import org.robolectric.shadows.ShadowNetworkInfo

@Config(sdk = [Build.VERSION_CODES.LOLLIPOP_MR1])
@RunWith(RobolectricTestRunner::class)
@Implements(ShadowConnectivityManager::class)
class ConfigProviderTest {

    private lateinit var shadowOfActiveNetworkInfo: ShadowNetworkInfo

    private lateinit var configProvider: ConfigProvider

    @Before
    fun setUp() {
        val application: Application = ApplicationProvider.getApplicationContext()
        val connectivityManager: ConnectivityManager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        shadowOfActiveNetworkInfo = Shadows.shadowOf(connectivityManager.activeNetworkInfo)
        configProvider = ConfigProviderImpl(application)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `test isOnline`() {
        // GIVEN
        @Suppress("DEPRECATION")
        shadowOfActiveNetworkInfo.setConnectionStatus(NetworkInfo.State.CONNECTED)

        // WHEN
        val isOnline: Boolean = configProvider.isOnline()

        // THEN
        isOnline.shouldBeTrue()
    }

    @Test
    fun `test isOffline`() {
        // GIVEN
        @Suppress("DEPRECATION")
        shadowOfActiveNetworkInfo.setConnectionStatus(NetworkInfo.State.DISCONNECTED)

        // WHEN
        val isOnline: Boolean = configProvider.isOnline()

        // THEN
        isOnline.shouldBeFalse()
    }
}
