package com.mohsenoid.rickandmorty

import com.mohsenoid.rickandmorty.injection.AndroidModule
import com.mohsenoid.rickandmorty.injection.DataModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidModule::class,
        AndroidSupportInjectionModule::class,
        RickAndMortyApplicationModule::class,
        RickAndMortyApplicationBuildersModule::class,
        DataModule::class
    ]
)
interface RickAndMortyApplicationComponent {

    fun inject(application: RickAndMortyApplication)
}
