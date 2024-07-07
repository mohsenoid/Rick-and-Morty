package com.mohsenoid.rickandmorty.data

import com.mohsenoid.rickandmorty.data.characters.CharacterRepositoryImpl
import com.mohsenoid.rickandmorty.data.db.databaseModule
import com.mohsenoid.rickandmorty.data.episodes.EpisodeRepositoryImpl
import com.mohsenoid.rickandmorty.data.remote.remoteModule
import com.mohsenoid.rickandmorty.domain.characters.CharacterRepository
import com.mohsenoid.rickandmorty.domain.episodes.EpisodeRepository
import org.koin.dsl.module

private val dataModule =
    module {
        single<EpisodeRepository> {
            EpisodeRepositoryImpl(
                episodeApiService = get(),
                episodeDao = get(),
            )
        }

        single<CharacterRepository> {
            CharacterRepositoryImpl(
                characterApiService = get(),
                characterDao = get(),
            )
        }
    }

val dataModules = dataModule + databaseModule + remoteModule
