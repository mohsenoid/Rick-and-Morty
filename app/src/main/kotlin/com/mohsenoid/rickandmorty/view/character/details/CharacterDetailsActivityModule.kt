package com.mohsenoid.rickandmorty.view.character.details

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CharacterDetailsActivityModule {

    @ContributesAndroidInjector(modules = [CharacterDetailsFragmentModule::class])
    abstract fun contributeCharacterDetailsFragment(): CharacterDetailsFragment
}
