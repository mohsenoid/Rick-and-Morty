package com.mohsenoid.rickandmorty.injection

import com.mohsenoid.rickandmorty.util.StatusProvider
import com.mohsenoid.rickandmorty.util.StatusProviderImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single<StatusProvider> { StatusProviderImpl(context = androidContext()) }
}
