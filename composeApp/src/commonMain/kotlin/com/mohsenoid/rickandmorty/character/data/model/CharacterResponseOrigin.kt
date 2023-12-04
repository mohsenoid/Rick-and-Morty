package com.mohsenoid.rickandmorty.character.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterResponseOrigin(
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String,
)
