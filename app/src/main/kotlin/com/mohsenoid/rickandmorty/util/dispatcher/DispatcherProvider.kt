package com.mohsenoid.rickandmorty.util.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

/**
 * [CoroutineDispatcher] provider.
 */
interface DispatcherProvider {

    /**
     * Main thread [CoroutineDispatcher] to be used for UI
     */
    val mainDispatcher: CoroutineDispatcher

    /**
     * IO thread [CoroutineDispatcher] to be used for long running IO tasks
     */
    val ioDispatcher: CoroutineDispatcher
}
