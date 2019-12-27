package com.mohsenoid.rickandmorty.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkOriginModel(

    @SerialName(value = "name")
    val name: String,

    @SerialName(value = "url")
    val url: String
)
