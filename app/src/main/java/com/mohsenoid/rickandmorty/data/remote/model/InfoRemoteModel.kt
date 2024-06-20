package com.mohsenoid.rickandmorty.data.remote.model


import com.google.gson.annotations.SerializedName

internal data class InfoRemoteModel(
    @SerializedName("count")
    val count: Int,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("next")
    val next: String?,
    @SerializedName("prev")
    val prev: String?,
)
