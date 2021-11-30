package com.mohsenoid.rickandmorty.domain.model

data class ModelCharacter(
    val id: Int,
    val name: String,
    val status: String,
    val isAlive: Boolean,
    val species: String,
    val type: String,
    val gender: String,
    val origin: ModelOrigin,
    val location: ModelLocation,
    val imageUrl: String,
    val episodeIds: List<Int>,
    val url: String,
    val created: String,
    val isKilledByUser: Boolean,
) {

    val isAliveAndNotKilledByUser: Boolean
        get() = isAlive && !isKilledByUser
}
