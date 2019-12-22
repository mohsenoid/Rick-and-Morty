package com.mohsenoid.rickandmorty.data.network.dto

import com.google.gson.annotations.SerializedName

data class NetworkOriginModel(

    @SerializedName("name")
    val name: String,

    @SerializedName("url")
    val url: String
)
