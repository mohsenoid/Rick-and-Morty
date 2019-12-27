package com.mohsenoid.rickandmorty.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkInfoModel(

    @SerialName(value = "count")
    val count: Int,

    @SerialName(value = "pages")
    val pages: Int,

    @SerialName(value = "next")
    val next: String,

    @SerialName(value = "prev")
    val prev: String
)
