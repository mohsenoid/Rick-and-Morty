package com.mohsenoid.rickandmorty.episode.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EpisodeResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("air_date")
    val airDate: String,
    @SerialName("episode")
    val episode: String,
    @SerialName("characters")
    val characters: List<String>,
    @SerialName("url")
    val url: String,
    @SerialName("created")
    val created: String,
)
