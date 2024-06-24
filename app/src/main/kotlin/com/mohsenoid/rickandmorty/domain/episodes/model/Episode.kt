package com.mohsenoid.rickandmorty.domain.episodes.model

import androidx.compose.runtime.Immutable

@Immutable
data class Episode(
    val id: Int,
    val name: String,
    val airDate: String,
    val episode: String,
    val characters: Set<Int>,
)
