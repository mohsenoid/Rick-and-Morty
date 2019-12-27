package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.dto.DbCharacterModel
import com.mohsenoid.rickandmorty.data.db.dto.DbEpisodeModel
import com.mohsenoid.rickandmorty.data.db.dto.DbLocationModel
import com.mohsenoid.rickandmorty.data.db.dto.DbOriginModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkCharacterModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkEpisodeModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkLocationModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkOriginModel
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity
import com.mohsenoid.rickandmorty.domain.entity.EpisodeEntity
import com.mohsenoid.rickandmorty.domain.entity.LocationEntity
import com.mohsenoid.rickandmorty.domain.entity.OriginEntity
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MapperModule {

    @Provides
    @Singleton
    fun provideEpisodeDbMapper(): Mapper<NetworkEpisodeModel, DbEpisodeModel> {
        return EpisodeDbMapper()
    }

    @Provides
    @Singleton
    fun provideEpisodeEntityMapper(): Mapper<DbEpisodeModel, EpisodeEntity> {
        return EpisodeEntityMapper()
    }

    @Provides
    @Singleton
    fun provideOriginDbMapper(): Mapper<NetworkOriginModel, DbOriginModel> {
        return OriginDbMapper()
    }

    @Provides
    @Singleton
    fun provideOriginEntityMapper(): Mapper<DbOriginModel, OriginEntity> {
        return OriginEntityMapper()
    }

    @Provides
    @Singleton
    fun provideLocationDbMapper(): Mapper<NetworkLocationModel, DbLocationModel> {
        return LocationDbMapper()
    }

    @Provides
    @Singleton
    fun provideLocationEntityMapper(): Mapper<DbLocationModel, LocationEntity> {
        return LocationEntityMapper()
    }

    @Provides
    @Singleton
    fun provideCharacterDbMapper(
        originDbMapper: Mapper<NetworkOriginModel, DbOriginModel>,
        locationDbMapper: Mapper<NetworkLocationModel, DbLocationModel>
    ): Mapper<NetworkCharacterModel, DbCharacterModel> {
        return CharacterDbMapper(originDbMapper, locationDbMapper)
    }

    @Provides
    @Singleton
    fun provideCharacterEntityMapper(
        originEntityMapper: Mapper<DbOriginModel, OriginEntity>,
        locationEntityMapper: Mapper<DbLocationModel, LocationEntity>
    ): Mapper<DbCharacterModel, CharacterEntity> {
        return CharacterEntityMapper(originEntityMapper, locationEntityMapper)
    }
}
