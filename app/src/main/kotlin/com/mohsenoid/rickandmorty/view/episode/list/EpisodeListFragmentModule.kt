package com.mohsenoid.rickandmorty.view.episode.list

import org.koin.core.qualifier.named
import org.koin.dsl.module

val episodeListFragmentModule = module {

    scope(named<EpisodeListFragment>()) {

        scoped<EpisodeListContract.Presenter> {
            EpisodeListPresenter(
                repository = get(),
                configProvider = get()
            )
        }
    }
}
