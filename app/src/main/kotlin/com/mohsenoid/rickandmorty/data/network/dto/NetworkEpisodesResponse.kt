package com.mohsenoid.rickandmorty.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkEpisodesResponse(

    @SerialName(value = "info")
    val info: NetworkInfoModel,

    @SerialName(value = "results")
    val results: List<NetworkEpisodeModel>
)
