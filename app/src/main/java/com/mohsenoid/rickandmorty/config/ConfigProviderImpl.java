package com.mohsenoid.rickandmorty.config;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConfigProviderImpl implements ConfigProvider {

    private static ConfigProviderImpl instance;

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
        return activeNetworkInfo != null;
    }
}
