package com.mohsenoid.rickandmorty.data.db

import org.koin.core.annotation.KoinReflectAPI
import org.koin.dsl.module
import org.koin.dsl.single

@OptIn(KoinReflectAPI::class)
val dbModule = module {
    single<DbRickAndMorty>()
}
