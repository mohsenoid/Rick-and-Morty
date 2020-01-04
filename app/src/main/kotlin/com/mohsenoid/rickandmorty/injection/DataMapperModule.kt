package com.mohsenoid.rickandmorty.injection

import com.mohsenoid.rickandmorty.data.db.dto.DbCharacterModel
import com.mohsenoid.rickandmorty.data.db.dto.DbEpisodeModel
import com.mohsenoid.rickandmorty.data.db.dto.DbLocationModel
import com.mohsenoid.rickandmorty.data.db.dto.DbOriginModel
import com.mohsenoid.rickandmorty.data.mapper.CharacterDbMapper
import com.mohsenoid.rickandmorty.data.mapper.CharacterEntityMapper
import com.mohsenoid.rickandmorty.data.mapper.EpisodeDbMapper
import com.mohsenoid.rickandmorty.data.mapper.EpisodeEntityMapper
import com.mohsenoid.rickandmorty.data.mapper.LocationDbMapper
import com.mohsenoid.rickandmorty.data.mapper.LocationEntityMapper
import com.mohsenoid.rickandmorty.data.mapper.Mapper
import com.mohsenoid.rickandmorty.data.mapper.OriginDbMapper
import com.mohsenoid.rickandmorty.data.mapper.OriginEntityMapper
import com.mohsenoid.rickandmorty.data.network.dto.NetworkCharacterModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkEpisodeModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkLocationModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkOriginModel
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity
import com.mohsenoid.rickandmorty.domain.entity.EpisodeEntity
import com.mohsenoid.rickandmorty.domain.entity.LocationEntity
import com.mohsenoid.rickandmorty.domain.entity.OriginEntity
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataMapperModule = module {

    single<Mapper<NetworkEpisodeModel, DbEpisodeModel>>(named<EpisodeDbMapper>()) { EpisodeDbMapper() }

    single<Mapper<DbEpisodeModel, EpisodeEntity>>(named<EpisodeEntityMapper>()) { EpisodeEntityMapper() }

    single<Mapper<NetworkOriginModel, DbOriginModel>>(named<OriginDbMapper>()) { OriginDbMapper() }

    single<Mapper<DbOriginModel, OriginEntity>>(named<OriginEntityMapper>()) { OriginEntityMapper() }

    single<Mapper<NetworkLocationModel, DbLocationModel>>(named<LocationDbMapper>()) { LocationDbMapper() }

    single<Mapper<DbLocationModel, LocationEntity>>(named<LocationEntityMapper>()) { LocationEntityMapper() }

    single<Mapper<NetworkCharacterModel, DbCharacterModel>>(named<CharacterDbMapper>()) {
        CharacterDbMapper(
            originDbMapper = get(named<OriginDbMapper>()),
            locationDbMapper = get(named<LocationDbMapper>())
        )
    }

    single<Mapper<DbCharacterModel, CharacterEntity>>(named<CharacterEntityMapper>()) {
        CharacterEntityMapper(
            originEntityMapper = get(named<OriginEntityMapper>()),
            locationEntityMapper = get(named<LocationEntityMapper>())
        )
    }
}
