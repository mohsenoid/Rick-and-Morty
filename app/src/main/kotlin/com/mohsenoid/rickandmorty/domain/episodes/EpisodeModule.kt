package com.mohsenoid.rickandmorty.domain.episodes

import com.mohsenoid.rickandmorty.domain.episodes.usecase.GetEpisodesUseCase
import org.koin.dsl.module

internal val episodeModule =
    module {
        factory {
            GetEpisodesUseCase(episodeRepository = get())
        }
    }
