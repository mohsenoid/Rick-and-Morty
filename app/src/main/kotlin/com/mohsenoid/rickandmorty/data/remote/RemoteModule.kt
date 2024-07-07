package com.mohsenoid.rickandmorty.data.remote

import com.mohsenoid.rickandmorty.BASE_URL_QUALIFIER
import com.mohsenoid.rickandmorty.data.characters.remote.CharacterApiService
import com.mohsenoid.rickandmorty.data.episodes.remote.EpisodeApiService
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

internal val remoteModule =
    module {
        single<Retrofit> {
            ApiServiceProvider.getRetrofit(get(named(BASE_URL_QUALIFIER)))
        }

        single<CharacterApiService> {
            val retrofit: Retrofit = get()
            retrofit.create(CharacterApiService::class.java)
        }

        single<EpisodeApiService> {
            val retrofit: Retrofit = get()
            retrofit.create(EpisodeApiService::class.java)
        }
    }
