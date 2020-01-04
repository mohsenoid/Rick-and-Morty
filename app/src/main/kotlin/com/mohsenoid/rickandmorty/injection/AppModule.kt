package com.mohsenoid.rickandmorty.injection

import com.mohsenoid.rickandmorty.injection.qualifier.QualifiersNames
import com.mohsenoid.rickandmorty.util.config.ConfigProvider
import com.mohsenoid.rickandmorty.util.config.ConfigProviderImpl
import com.mohsenoid.rickandmorty.util.dispatcher.AppDispatcherProvider
import com.mohsenoid.rickandmorty.util.dispatcher.DispatcherProvider
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    single<ConfigProvider> { ConfigProviderImpl(context = androidContext()) }

    single<DispatcherProvider> { AppDispatcherProvider() }

    single(named(QualifiersNames.MAIN_DISPATCHER)) { get<DispatcherProvider>().mainDispatcher }

    single(named(QualifiersNames.IO_DISPATCHER)) { get<DispatcherProvider>().ioDispatcher }
}
