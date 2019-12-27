package com.mohsenoid.rickandmorty

import com.mohsenoid.rickandmorty.data.DataModule
import com.mohsenoid.rickandmorty.injection.AndroidModule
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
