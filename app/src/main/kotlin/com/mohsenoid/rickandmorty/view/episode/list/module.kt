package com.mohsenoid.rickandmorty.view.episode.list

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.annotation.KoinReflectAPI
import org.koin.dsl.module
import org.koin.dsl.single

@OptIn(KoinReflectAPI::class)
val episodeListFragmentModule = module {

    single<EpisodeListSource>()

    viewModel<EpisodeListViewModel>()
}
