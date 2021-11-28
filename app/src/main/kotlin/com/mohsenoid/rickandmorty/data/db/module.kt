package com.mohsenoid.rickandmorty.data.db

import org.koin.dsl.module
import org.koin.dsl.single

val dbModule = module {
    single<DbRickAndMorty>()
}
