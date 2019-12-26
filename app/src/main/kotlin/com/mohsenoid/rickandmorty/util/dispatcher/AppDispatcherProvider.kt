package com.mohsenoid.rickandmorty.util.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Application [CoroutineDispatcher] provider.
 */
class AppDispatcherProvider : DispatcherProvider {

    /**
     * Main thread [CoroutineDispatcher] which uses [Dispatchers.Main]
     */
    override val mainDispatcher: CoroutineDispatcher = Dispatchers.Main

    /**
     * IO thread [CoroutineDispatcher] which uses [Dispatchers.IO]
     */
    override val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
}
