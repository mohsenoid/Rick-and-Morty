package com.mohsenoid.rickandmorty.episode.domain.model

data class Episode(
    val id: Int,
    val name: String,
    val episode: String,
    val characters: List<Character>,
)
