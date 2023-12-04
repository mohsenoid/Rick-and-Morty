package com.mohsenoid.rickandmorty.episodes.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EpisodesResponseInfo(
    @SerialName("count")
    val count: Int,
    @SerialName("pages")
    val pages: Int,
    @SerialName("next")
    val next: String,
    @SerialName("prev")
    val prev: String?,
)
