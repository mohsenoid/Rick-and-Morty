package com.mohsenoid.rickandmorty.view.episode.list

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class EpisodeListActivityModule {

    @ContributesAndroidInjector(modules = [EpisodeListFragmentModule::class])
    abstract fun contributeEpisodeListFragment(): EpisodeListFragment
}
