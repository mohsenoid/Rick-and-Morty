package com.mohsenoid.rickandmorty.injection

import com.mohsenoid.rickandmorty.data.RepositoryImpl
import com.mohsenoid.rickandmorty.data.mapper.CharacterDbMapper
import com.mohsenoid.rickandmorty.data.mapper.CharacterEntityMapper
import com.mohsenoid.rickandmorty.data.mapper.EpisodeDbMapper
import com.mohsenoid.rickandmorty.data.mapper.EpisodeEntityMapper
import com.mohsenoid.rickandmorty.domain.Repository
import com.mohsenoid.rickandmorty.injection.qualifier.QualifiersNames
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = dataDbModule + dataMapperModule + dataNetworkModule + module {

    single<Repository> {
        RepositoryImpl(
            characterDao = get(),
            episodeDao = get(),
            networkClient = get(),
            ioDispatcher = get(named(QualifiersNames.IO_DISPATCHER)),
            configProvider = get(),
            episodeDbMapper = get(named<EpisodeDbMapper>()),
            episodeEntityMapper = get(named<EpisodeEntityMapper>()),
            characterDbMapper = get(named<CharacterDbMapper>()),
            characterEntityMapper = get(named<CharacterEntityMapper>())
        )
    }
}
