package com.mohsenoid.rickandmorty.data.network.dto

import com.google.gson.annotations.SerializedName

data class NetworkCharacterModel(

    @SerializedName(value = "id")
    val id: Int,

    @SerializedName(value = "name")
    val name: String,

    @SerializedName(value = "status")
    val status: String,

    @SerializedName(value = "species")
    val species: String,

    @SerializedName(value = "type")
    val type: String,

    @SerializedName(value = "gender")
    val gender: String,

    @SerializedName(value = "origin")
    val origin: NetworkOriginModel,

    @SerializedName(value = "location")
    val location: NetworkLocationModel,

    @SerializedName(value = "image")
    val image: String,

    @SerializedName(value = "episode")
    val episodes: List<String>,

    @SerializedName(value = "url")
    val url: String,

    @SerializedName(value = "created")
    val created: String
)
