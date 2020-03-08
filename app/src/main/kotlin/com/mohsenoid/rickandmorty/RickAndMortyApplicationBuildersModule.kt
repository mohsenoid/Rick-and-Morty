package com.mohsenoid.rickandmorty

import com.mohsenoid.rickandmorty.injection.scope.PerActivity
import com.mohsenoid.rickandmorty.view.MainActivity
import com.mohsenoid.rickandmorty.view.MainActivityModule
import com.mohsenoid.rickandmorty.view.character.details.CharacterDetailsFragment
import com.mohsenoid.rickandmorty.view.character.details.CharacterDetailsFragmentModule
import com.mohsenoid.rickandmorty.view.character.list.CharacterListFragment
import com.mohsenoid.rickandmorty.view.character.list.CharacterListFragmentModule
import com.mohsenoid.rickandmorty.view.episode.list.EpisodeListFragment
import com.mohsenoid.rickandmorty.view.episode.list.EpisodeListFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Binds all sub-components except MainService within the app.
 */
@Module
abstract class RickAndMortyApplicationBuildersModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainActivity(): MainActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [EpisodeListFragmentModule::class])
    abstract fun contributeEpisodeListFragment(): EpisodeListFragment

    @PerActivity
    @ContributesAndroidInjector(modules = [CharacterListFragmentModule::class])
    abstract fun contributeCharacterListFragment(): CharacterListFragment

    @PerActivity
    @ContributesAndroidInjector(modules = [CharacterDetailsFragmentModule::class])
    abstract fun contributeCharacterDetailsFragment(): CharacterDetailsFragment
}
