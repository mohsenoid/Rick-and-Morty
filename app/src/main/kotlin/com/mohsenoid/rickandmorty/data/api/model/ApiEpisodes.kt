package com.mohsenoid.rickandmorty.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ApiEpisodes(

    @SerialName(value = "info")
    val info: ApiInfo,

    @SerialName(value = "results")
    val results: List<ApiEpisode>,
)
