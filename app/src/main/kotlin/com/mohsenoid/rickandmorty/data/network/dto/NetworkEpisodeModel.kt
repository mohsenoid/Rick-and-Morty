package com.mohsenoid.rickandmorty.data.network.dto

import com.google.gson.annotations.SerializedName

data class NetworkEpisodeModel(

    @SerializedName(value = "id")
    val id: Int,

    @SerializedName(value = "name")
    val name: String,

    @SerializedName(value = "air_date")
    val airDate: String,

    @SerializedName(value = "episode")
    val episode: String,

    @SerializedName(value = "characters")
    val characters: List<String>,

    @SerializedName(value = "url")
    val url: String,

    @SerializedName(value = "created")
    val created: String
)
