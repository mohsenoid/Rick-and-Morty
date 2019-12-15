package com.mohsenoid.rickandmorty.config;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

@SuppressLint("StaticFieldLeak")
public class ConfigProviderImpl implements ConfigProvider {

    private static ConfigProviderImpl instance;

    private Context context;

    private ConfigProviderImpl(Context context) {
        this.context = context;
    }

    public static ConfigProviderImpl getInstance(Context context) {
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
