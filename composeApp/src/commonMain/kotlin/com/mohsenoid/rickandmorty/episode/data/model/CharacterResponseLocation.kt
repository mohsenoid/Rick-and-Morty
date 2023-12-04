package com.mohsenoid.rickandmorty.episode.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterResponseLocation(
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String,
)
