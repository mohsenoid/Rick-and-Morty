package com.mohsenoid.rickandmorty.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities

/**
 *
 * This class implements [StatusProvider] and provides configurations required on runtime.
 *
 * @constructor Creates a ConfigProvider using the Android Application context.
 * @param context the Android Application context.
 */
class StatusProviderImpl(private val context: Context) : StatusProvider {

    /**
     * This function uses [ConnectivityManager]  to check active Network Capabilities to know
     * the Android phone network connectivity status.
     *
     * @return is the phone connected to a network through cellular or WiFi.
     */
    @Suppress("ReturnCount")
    override fun isOnline(): Boolean {
        val connectivityManager: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
                ?: return false

        val activeNetwork: Network = connectivityManager.activeNetwork ?: return false

        val networkCapabilities: NetworkCapabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }
}
