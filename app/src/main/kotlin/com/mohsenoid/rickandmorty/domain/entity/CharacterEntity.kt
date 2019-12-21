package com.mohsenoid.rickandmorty.domain.entity

class CharacterEntity(
    val id: Int,
    val name: String,
    val status: String,
    val statusAlive: Boolean,
    val species: String,
    val type: String,
    val gender: String,
    val origin: OriginEntity,
    val location: LocationEntity,
    val imageUrl: String,
    val episodeIds: List<Int>,
    val url: String,
    val created: String,
    val killedByUser: Boolean
) {
    val isAlive: Boolean
        get() = statusAlive && !killedByUser
}
