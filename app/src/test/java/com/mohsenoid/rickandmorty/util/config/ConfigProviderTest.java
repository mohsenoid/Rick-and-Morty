package com.mohsenoid.rickandmorty.util.config;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.annotation.Implements;
import org.robolectric.shadows.ShadowConnectivityManager;
import org.robolectric.shadows.ShadowNetworkInfo;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

@Config(sdk = Build.VERSION_CODES.P)
@RunWith(RobolectricTestRunner.class)
@Implements(ShadowConnectivityManager.class)
public class ConfigProviderTest {

    private ShadowNetworkInfo shadowOfActiveNetworkInfo;

    private ConfigProvider configProvider;

    @Before
    public void setUp() {
        Application application = getApplicationContext();

        ConnectivityManager connectivityManager = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        shadowOfActiveNetworkInfo = shadowOf(connectivityManager.getActiveNetworkInfo());

        configProvider = ConfigProviderImpl.getInstance(application);
    }

    @After
    public void tearDown() {
        ConfigProviderImpl.instance = null;
    }

    @Test
    public void testIsOnline() {
        // GIVEN
        shadowOfActiveNetworkInfo.setConnectionStatus(NetworkInfo.State.CONNECTED);

        // WHEN
        boolean isOnline = configProvider.isOnline();

        //THEN
        assertTrue(isOnline);
    }

    @Test
    public void testIsOffline() {
        // GIVEN
        shadowOfActiveNetworkInfo.setConnectionStatus(NetworkInfo.State.DISCONNECTED);

        // WHEN
        boolean isOnline = configProvider.isOnline();

        //THEN
        assertFalse(isOnline);
    }
}