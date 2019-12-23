package com.mohsenoid.rickandmorty.data.network.dto

import com.google.gson.annotations.SerializedName

data class NetworkCharacterModel(

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("status")
    val status: String,

    @SerializedName("species")
    val species: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("gender")
    val gender: String,

    @SerializedName("origin")
    val origin: NetworkOriginModel,

    @SerializedName("location")
    val location: NetworkLocationModel,

    @SerializedName("image")
    val image: String,

    @SerializedName("episode")
    val episodes: List<String>,

    @SerializedName("url")
    val url: String,

    @SerializedName("created")
    val created: String
)
