package com.mohsenoid.rickandmorty.data.network.dto

import com.google.gson.annotations.SerializedName

data class NetworkEpisodesResponse(

    @SerializedName(value = "info")
    val info: NetworkInfoModel,

    @SerializedName(value = "results")
    val results: List<NetworkEpisodeModel>
)
