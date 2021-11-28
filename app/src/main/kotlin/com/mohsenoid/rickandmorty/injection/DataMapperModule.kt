package com.mohsenoid.rickandmorty.injection

import com.mohsenoid.rickandmorty.data.db.entity.DbEntityCharacter
import com.mohsenoid.rickandmorty.data.db.entity.DbEntityEpisode
import com.mohsenoid.rickandmorty.data.db.entity.DbEntityLocation
import com.mohsenoid.rickandmorty.data.db.entity.DbEntityOrigin
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

    single<Mapper<NetworkEpisodeModel, DbEntityEpisode>>(named<EpisodeDbMapper>()) { EpisodeDbMapper() }

    single<Mapper<DbEntityEpisode, EpisodeEntity>>(named<EpisodeEntityMapper>()) { EpisodeEntityMapper() }

    single<Mapper<NetworkOriginModel, DbEntityOrigin>>(named<OriginDbMapper>()) { OriginDbMapper() }

    single<Mapper<DbEntityOrigin, OriginEntity>>(named<OriginEntityMapper>()) { OriginEntityMapper() }

    single<Mapper<NetworkLocationModel, DbEntityLocation>>(named<LocationDbMapper>()) { LocationDbMapper() }

    single<Mapper<DbEntityLocation, LocationEntity>>(named<LocationEntityMapper>()) { LocationEntityMapper() }

    single<Mapper<NetworkCharacterModel, DbEntityCharacter>>(named<CharacterDbMapper>()) {
        CharacterDbMapper(
            originDbMapper = get(named<OriginDbMapper>()),
            locationDbMapper = get(named<LocationDbMapper>())
        )
    }

    single<Mapper<DbEntityCharacter, CharacterEntity>>(named<CharacterEntityMapper>()) {
        CharacterEntityMapper(
            originEntityMapper = get(named<OriginEntityMapper>()),
            locationEntityMapper = get(named<LocationEntityMapper>())
        )
    }
}
