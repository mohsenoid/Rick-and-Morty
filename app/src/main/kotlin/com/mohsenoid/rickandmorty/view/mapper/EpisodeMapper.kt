package com.mohsenoid.rickandmorty.view.mapper

import com.mohsenoid.rickandmorty.domain.model.ModelEpisode
import com.mohsenoid.rickandmorty.view.model.ViewEpisodeItem

fun ModelEpisode.toViewEpisodeItem(onClick: () -> Unit) = ViewEpisodeItem(
    name = name,
    airDate = airDate,
    episode = episode,
    onClick = onClick,
)
