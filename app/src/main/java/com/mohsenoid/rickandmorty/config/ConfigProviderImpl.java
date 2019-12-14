package com.mohsenoid.rickandmorty.config;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConfigProviderImpl implements ConfigProvider {

    private Context context;

    public ConfigProviderImpl(Context context) {
        this.context = context;
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
