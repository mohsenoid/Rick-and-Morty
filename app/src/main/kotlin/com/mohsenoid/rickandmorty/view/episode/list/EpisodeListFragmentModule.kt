package com.mohsenoid.rickandmorty.view.episode.list

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val episodeListFragmentModule = module {

    viewModel {
        EpisodeListViewModel(
            application = get(),
            repository = get(),
            configProvider = get()
        )
    }
}
