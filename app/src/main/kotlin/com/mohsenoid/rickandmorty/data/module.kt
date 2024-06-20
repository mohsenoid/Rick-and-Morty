package com.mohsenoid.rickandmorty.data

import com.mohsenoid.rickandmorty.data.api.apiModule
import com.mohsenoid.rickandmorty.data.db.dbModule
import com.mohsenoid.rickandmorty.domain.Repository
import org.koin.dsl.module

val dataModule = dbModule + apiModule + module {

    single<Repository> {
        RepositoryImpl(
            db = get(),
            api = get(),
            statusProvider = get(),
        )
    }
}
