package com.mohsenoid.rickandmorty.data.characters.remote.model

import com.google.gson.annotations.SerializedName

internal data class OriginRemoteModel(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
)
