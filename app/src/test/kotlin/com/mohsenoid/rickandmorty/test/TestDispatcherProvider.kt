package com.mohsenoid.rickandmorty.test

import com.mohsenoid.rickandmorty.util.dispatcher.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class TestDispatcherProvider : DispatcherProvider {
    override val mainDispatcher: CoroutineDispatcher = Dispatchers.Unconfined
    override val ioDispatcher: CoroutineDispatcher = Dispatchers.Unconfined
}
