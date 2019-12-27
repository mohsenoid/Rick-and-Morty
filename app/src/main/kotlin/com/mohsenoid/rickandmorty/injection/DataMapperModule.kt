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
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DataMapperModule {

    @Binds
    @Singleton
    abstract fun provideEpisodeDbMapper(mapper: EpisodeDbMapper): Mapper<NetworkEpisodeModel, DbEpisodeModel>

    @Binds
    @Singleton
    abstract fun provideEpisodeEntityMapper(mapper: EpisodeEntityMapper): Mapper<DbEpisodeModel, EpisodeEntity>

    @Binds
    @Singleton
    abstract fun provideOriginDbMapper(mapper: OriginDbMapper): Mapper<NetworkOriginModel, DbOriginModel>

    @Binds
    @Singleton
    abstract fun provideOriginEntityMapper(mapper: OriginEntityMapper): Mapper<DbOriginModel, OriginEntity>

    @Binds
    @Singleton
    abstract fun provideLocationDbMapper(mapper: LocationDbMapper): Mapper<NetworkLocationModel, DbLocationModel>

    @Binds
    @Singleton
    abstract fun provideLocationEntityMapper(mapper: LocationEntityMapper): Mapper<DbLocationModel, LocationEntity>

    @Binds
    @Singleton
    abstract fun provideCharacterDbMapper(mapper: CharacterDbMapper): Mapper<NetworkCharacterModel, DbCharacterModel>

    @Binds
    @Singleton
    abstract fun provideCharacterEntityMapper(mapper: CharacterEntityMapper): Mapper<DbCharacterModel, CharacterEntity>
}
