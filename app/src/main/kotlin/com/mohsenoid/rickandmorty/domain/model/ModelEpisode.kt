package com.mohsenoid.rickandmorty.domain.model

data class ModelEpisode(
    val id: Int,
    val name: String,
    val airDate: String,
    val episode: String,
    val characterIds: List<Int>,
    val url: String,
    val created: String,
)
