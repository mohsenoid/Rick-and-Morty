package com.mohsenoid.rickandmorty.view.episode.list

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.annotation.KoinReflectAPI
import org.koin.dsl.module

@OptIn(KoinReflectAPI::class)
val episodeListFragmentModule = module {

    viewModel<EpisodeListViewModel>()
}
