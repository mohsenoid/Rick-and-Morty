package com.mohsenoid.rickandmorty.episodes.domain.model

data class Episode(
    val id: Int,
    val name: String,
    val airDate: String,
    val episode: String,
    val characters: List<String>,
    val created: String,
)
