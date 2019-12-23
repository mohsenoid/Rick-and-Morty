package com.mohsenoid.rickandmorty.data.network.dto

import com.google.gson.annotations.SerializedName

data class NetworkEpisodeModel(

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("air_date")
    val airDate: String,

    @SerializedName("episode")
    val episode: String,

    @SerializedName("characters")
    val characters: List<String>,

    @SerializedName("url")
    val url: String,

    @SerializedName("created")
    val created: String
)
