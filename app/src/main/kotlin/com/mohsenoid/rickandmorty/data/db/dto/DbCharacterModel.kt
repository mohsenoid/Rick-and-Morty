package com.mohsenoid.rickandmorty.data.db.dto

data class DbCharacterModel(
    val id: Int,
    val name: String,
    val status: String,
    val statusAlive: Boolean,
    val species: String,
    val type: String,
    val gender: String,
    val origin: DbOriginModel,
    val location: DbLocationModel,
    val image: String,
    val serializedEpisodes: String,
    val url: String,
    val created: String,
    val killedByUser: Boolean
)
