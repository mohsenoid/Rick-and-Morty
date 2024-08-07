package com.mohsenoid.rickandmorty.data.episodes.remote.model

import com.google.gson.annotations.SerializedName

internal data class EpisodesResponse(
    @SerializedName("info")
    val info: InfoRemoteModel,
    @SerializedName("results")
    val results: List<EpisodeRemoteModel>,
)
