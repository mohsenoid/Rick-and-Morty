package com.mohsenoid.rickandmorty.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCharacterModel(

    @SerialName(value = "id")
    val id: Int,

    @SerialName(value = "name")
    val name: String,

    @SerialName(value = "status")
    val status: String,

    @SerialName(value = "species")
    val species: String,

    @SerialName(value = "type")
    val type: String,

    @SerialName(value = "gender")
    val gender: String,

    @SerialName(value = "origin")
    val origin: NetworkOriginModel,

    @SerialName(value = "location")
    val location: NetworkLocationModel,

    @SerialName(value = "image")
    val image: String,

    @SerialName(value = "episode")
    val episodes: List<String>,

    @SerialName(value = "url")
    val url: String,

    @SerialName(value = "created")
    val created: String
)
