package com.mohsenoid.rickandmorty.view.episode.list

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val episodeListFragmentModule = module {

    viewModel<EpisodeListViewModel>()
}
