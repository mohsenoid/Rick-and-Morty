package com.mohsenoid.rickandmorty.domain.entity

data class EpisodeEntity(
    val id: Int,
    val name: String,
    val airDate: String,
    val episode: String,
    val characterIds: List<Int>,
    val url: String,
    val created: String
)
