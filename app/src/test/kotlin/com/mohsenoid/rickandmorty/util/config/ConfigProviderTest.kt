package com.mohsenoid.rickandmorty.util.config

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
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
        val application = ApplicationProvider.getApplicationContext<Application>()
        val connectivityManager =
            (application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        shadowOfActiveNetworkInfo = Shadows.shadowOf(connectivityManager.activeNetworkInfo)
        configProvider = ConfigProviderImpl(application)
    }

    @Test
    fun `test isOnline`() {
        // GIVEN
        shadowOfActiveNetworkInfo.setConnectionStatus(NetworkInfo.State.CONNECTED)

        // WHEN
        val isOnline = configProvider.isOnline()

        //THEN
        assertTrue(isOnline)
    }

    @Test
    fun `test isOffline`() {
        // GIVEN
        shadowOfActiveNetworkInfo.setConnectionStatus(NetworkInfo.State.DISCONNECTED)

        // WHEN
        val isOnline = configProvider.isOnline()

        //THEN
        assertFalse(isOnline)
    }
}
