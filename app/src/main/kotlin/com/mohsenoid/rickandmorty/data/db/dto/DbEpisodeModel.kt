package com.mohsenoid.rickandmorty.data.db.dto

data class DbEpisodeModel(
    val id: Int,
    val name: String,
    val airDate: String,
    val episode: String,
    val serializedCharacterIds: String,
    val url: String,
    val created: String
)
