package com.mohsenoid.rickandmorty.data

import com.mohsenoid.rickandmorty.data.characters.CharacterRepositoryImpl
import com.mohsenoid.rickandmorty.data.db.Database
import com.mohsenoid.rickandmorty.data.db.DatabaseProvider
import com.mohsenoid.rickandmorty.data.episodes.EpisodeRepositoryImpl
import com.mohsenoid.rickandmorty.data.remote.ApiService
import com.mohsenoid.rickandmorty.domain.characters.CharacterRepository
import com.mohsenoid.rickandmorty.domain.episodes.EpisodeRepository
import org.koin.dsl.module
import retrofit2.Retrofit

val dataModule =
    module {

        single {
            val retrofit: Retrofit = get()
            retrofit.create(ApiService::class.java)
        }

        single {
            DatabaseProvider.getDatabase(context = get())
        }

        factory {
            val db: Database = get()
            db.characterDao()
        }

        factory {
            val db: Database = get()
            db.episodeDao()
        }

        single<EpisodeRepository> {
            EpisodeRepositoryImpl(
                apiService = get(),
                episodeDao = get(),
            )
        }

        single<CharacterRepository> {
            CharacterRepositoryImpl(
                apiService = get(),
                characterDao = get(),
            )
        }
    }
