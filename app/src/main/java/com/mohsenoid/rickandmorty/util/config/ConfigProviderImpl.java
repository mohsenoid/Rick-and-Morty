package com.mohsenoid.rickandmorty.util.config;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.VisibleForTesting;

public class ConfigProviderImpl implements ConfigProvider {

    @VisibleForTesting
    public static ConfigProviderImpl instance;

    private final Application context;

    private ConfigProviderImpl(Application context) {
        this.context = context;
    }

    public static synchronized ConfigProviderImpl getInstance(Application context) {
        if (instance == null)
            instance = new ConfigProviderImpl(context);

        return instance;
    }

    @Override
    public boolean isOnline() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) return false;

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null) return false;

        return connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
