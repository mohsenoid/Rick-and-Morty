package com.mohsenoid.rickandmorty.data.remote.model

import com.google.gson.annotations.SerializedName

internal data class CharacterRemoteModel(
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
    val origin: OriginRemoteModel,
    @SerializedName("location")
    val location: LocationRemoteModel,
    @SerializedName("image")
    val image: String,
    @SerializedName("episode")
    val episode: List<String>,
    @SerializedName("url")
    val url: String,
    @SerializedName("created")
    val created: String,
)
