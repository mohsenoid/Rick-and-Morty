package com.mohsenoid.rickandmorty.util.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    val mainDispatcher: CoroutineDispatcher
    val ioDispatcher: CoroutineDispatcher
}
