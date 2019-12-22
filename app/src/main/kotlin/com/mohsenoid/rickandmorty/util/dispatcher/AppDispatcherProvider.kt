package com.mohsenoid.rickandmorty.util.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class AppDispatcherProvider : DispatcherProvider {

    override val mainDispatcher: CoroutineDispatcher = Dispatchers.Main

    override val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
}