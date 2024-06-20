package com.mohsenoid.rickandmorty.data.remote.model

import com.google.gson.annotations.SerializedName

internal data class LocationRemoteModel(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
)
