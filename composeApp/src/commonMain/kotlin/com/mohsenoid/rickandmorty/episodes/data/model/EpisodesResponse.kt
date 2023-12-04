package com.mohsenoid.rickandmorty.episodes.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EpisodesResponse(
    @SerialName("info")
    val episodesResponseInfo: EpisodesResponseInfo,
    @SerialName("results")
    val episodesResponseResults: List<EpisodesResponseResult>,
)
