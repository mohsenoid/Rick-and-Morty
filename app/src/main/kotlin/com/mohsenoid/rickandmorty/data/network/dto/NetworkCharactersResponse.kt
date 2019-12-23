package com.mohsenoid.rickandmorty.data.network.dto

import com.google.gson.annotations.SerializedName

data class NetworkCharactersResponse(
    @SerializedName("results")
    val results: List<NetworkCharacterModel>
)
