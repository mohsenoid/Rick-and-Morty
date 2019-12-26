package com.mohsenoid.rickandmorty.util.config

/**
 * Application Config Provider.
 */
interface ConfigProvider {

    /**
     * Checks network connectivity status.
     */
    fun isOnline(): Boolean
}
