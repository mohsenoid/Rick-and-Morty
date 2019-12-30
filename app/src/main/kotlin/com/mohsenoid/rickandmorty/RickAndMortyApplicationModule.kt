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
import kotlinx.coroutines.CoroutineDispatcher
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
    fun provideConfigProvider(@ApplicationContext context: Application): ConfigProvider {
        return ConfigProviderImpl(context = context)
    }

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider = AppDispatcherProvider()

    @Provides
    @Singleton
    @Named(QualifiersNames.MAIN_DISPATCHER)
    fun provideMainDispatcher(dispatcherProvider: DispatcherProvider): CoroutineDispatcher {
        return dispatcherProvider.mainDispatcher
    }

    @Provides
    @Singleton
    @Named(QualifiersNames.IO_DISPATCHER)
    fun provideIoDispatcher(dispatcherProvider: DispatcherProvider): CoroutineDispatcher {
        return dispatcherProvider.ioDispatcher
    }
}
