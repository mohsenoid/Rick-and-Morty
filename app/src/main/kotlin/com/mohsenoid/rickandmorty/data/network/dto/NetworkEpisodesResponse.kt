package com.mohsenoid.rickandmorty.data.network.dto

import com.google.gson.annotations.SerializedName

data class NetworkEpisodesResponse(

    @SerializedName("info")
    val info: NetworkInfoModel,

    @SerializedName("results")
    val results: List<NetworkEpisodeModel>
)
