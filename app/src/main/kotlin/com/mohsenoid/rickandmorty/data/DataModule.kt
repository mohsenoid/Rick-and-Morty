package com.mohsenoid.rickandmorty.data

import com.mohsenoid.rickandmorty.data.db.DbModule
import com.mohsenoid.rickandmorty.data.mapper.MapperModule
import com.mohsenoid.rickandmorty.data.network.NetworkModule
import com.mohsenoid.rickandmorty.domain.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DbModule::class, NetworkModule::class, MapperModule::class])
class DataModule {

    @Singleton
    @Provides
    fun provideRepository(repository: RepositoryImpl): Repository {
        return repository
    }
}
