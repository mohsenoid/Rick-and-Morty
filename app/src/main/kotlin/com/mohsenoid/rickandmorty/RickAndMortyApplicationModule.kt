package com.mohsenoid.rickandmorty

import android.app.Application

import com.mohsenoid.rickandmorty.injection.qualifier.ApplicationContext
import com.mohsenoid.rickandmorty.injection.qualifier.QualifiersNames
import com.mohsenoid.rickandmorty.util.config.ConfigProvider
import com.mohsenoid.rickandmorty.util.config.ConfigProviderImpl
import com.mohsenoid.rickandmorty.util.dispatcher.AppDispatcherProvider
import com.mohsenoid.rickandmorty.util.dispatcher.DispatcherProvider
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import javax.inject.Named
import javax.inject.Singleton

@Module
class RickAndMortyApplicationModule {

    @Provides
    @Singleton
    @Named(QualifiersNames.IS_DEBUG)
    fun provideIsDebug(): Boolean = BuildConfig.DEBUG

    @Provides
    @Singleton
    fun provideConfigProvider(@ApplicationContext context: Application): ConfigProvider =
        ConfigProviderImpl(context = context)

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider = AppDispatcherProvider()

    @Provides
    @Singleton
    fun providejson(): Json = Json(JsonConfiguration.Stable)
}
