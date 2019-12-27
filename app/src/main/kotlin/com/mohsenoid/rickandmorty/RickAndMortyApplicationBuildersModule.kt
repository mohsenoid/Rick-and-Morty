package com.mohsenoid.rickandmorty

import com.mohsenoid.rickandmorty.injection.scope.PerActivity
import com.mohsenoid.rickandmorty.view.character.details.CharacterDetailsActivity
import com.mohsenoid.rickandmorty.view.character.details.CharacterDetailsActivityModule
import com.mohsenoid.rickandmorty.view.character.list.CharacterListActivity
import com.mohsenoid.rickandmorty.view.character.list.CharacterListActivityModule
import com.mohsenoid.rickandmorty.view.episode.list.EpisodeListActivity
import com.mohsenoid.rickandmorty.view.episode.list.EpisodeListActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Binds all sub-components except MainService within the app.
 */
@Module
abstract class RickAndMortyApplicationBuildersModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [EpisodeListActivityModule::class])
    abstract fun bindEpisodeListActivity(): EpisodeListActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [CharacterListActivityModule::class])
    abstract fun bindCharacterListActivity(): CharacterListActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [CharacterDetailsActivityModule::class])
    abstract fun bindCharacterDetailsActivity(): CharacterDetailsActivity
}
