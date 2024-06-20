package com.mohsenoid.rickandmorty.data.remote.model


import com.google.gson.annotations.SerializedName

internal data class EpisodeRemoteModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("air_date")
    val airDate: String,
    @SerializedName("episode")
    val episode: String,
    @SerializedName("characters")
    val characters: Set<String>,
    @SerializedName("url")
    val url: String,
    @SerializedName("created")
    val created: String,
)
