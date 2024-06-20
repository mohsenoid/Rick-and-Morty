package com.mohsenoid.rickandmorty.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ApiLocation(

    @SerialName(value = "name")
    val name: String,

    @SerialName(value = "url")
    val url: String,
)
