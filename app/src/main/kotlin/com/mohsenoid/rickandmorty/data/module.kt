package com.mohsenoid.rickandmorty.injection

import com.mohsenoid.rickandmorty.data.RepositoryImpl
import com.mohsenoid.rickandmorty.data.db.dbModule
import com.mohsenoid.rickandmorty.domain.Repository
import org.koin.dsl.module

val dataModule = dbModule + dataNetworkModule + module {

    single<Repository> {
        RepositoryImpl(
            db = get(),
            networkClient = get(),
            configProvider = get(),
        )
    }
}
