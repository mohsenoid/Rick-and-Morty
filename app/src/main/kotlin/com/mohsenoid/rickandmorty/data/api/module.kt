package com.mohsenoid.rickandmorty.data.api

import com.mohsenoid.rickandmorty.util.KoinQualifiersNames
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

@OptIn(ExperimentalSerializationApi::class)
val apiModule = module {

    single {
        ApiRickAndMorty(
            applicationContext = androidApplication(),
            baseUrl = getProperty(KoinQualifiersNames.BASE_URL),
        )
    }
}
