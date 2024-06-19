package com.mohsenoid.rickandmorty.data

import com.mohsenoid.rickandmorty.data.characters.CharactersRepositoryImpl
import com.mohsenoid.rickandmorty.data.db.Database
import com.mohsenoid.rickandmorty.data.db.DatabaseProvider
import com.mohsenoid.rickandmorty.data.episodes.EpisodesRepositoryImpl
import com.mohsenoid.rickandmorty.data.remote.ApiService
import com.mohsenoid.rickandmorty.domain.characters.CharactersRepository
import com.mohsenoid.rickandmorty.domain.episodes.EpisodesRepository
import org.koin.dsl.module
import retrofit2.Retrofit


val dataModule = module {

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

    single<EpisodesRepository> {
        EpisodesRepositoryImpl(
            apiService = get(),
            episodesDao = get(),
        )
    }

    single<CharactersRepository> {
        CharactersRepositoryImpl(
            apiService = get(),
            characterDao = get(),
        )
    }
}
