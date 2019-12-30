package com.mohsenoid.rickandmorty.view.character.list

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CharacterListActivityModule {

    @ContributesAndroidInjector(modules = [CharacterListFragmentModule::class])
    abstract fun contributeCharacterListFragment(): CharacterListFragment
}
