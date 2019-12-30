package com.mohsenoid.rickandmorty.injection

import com.mohsenoid.rickandmorty.data.RepositoryImpl
import com.mohsenoid.rickandmorty.domain.Repository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(includes = [DataDbModule::class, DataNetworkModule::class, DataMapperModule::class])
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun provideRepository(repository: RepositoryImpl): Repository
}
