package com.mohsenoid.rickandmorty.data.network.dto

import com.google.gson.annotations.SerializedName

data class NetworkLocationModel(

    @SerializedName(value = "name")
    val name: String,

    @SerializedName(value = "url")
    val url: String
)
