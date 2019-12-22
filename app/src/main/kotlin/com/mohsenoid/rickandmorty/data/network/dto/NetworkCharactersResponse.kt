package com.mohsenoid.rickandmorty.data.network.dto

import org.json.JSONArray

data class NetworkCharactersResponse(val results: List<NetworkCharacterModel>) {

    companion object {
        fun fromJson(json: String): NetworkCharactersResponse {
            val jsonArray = JSONArray(json)
            val results = NetworkCharacterModel.fromJson(jsonArray)
            return NetworkCharactersResponse(results = results)
        }
    }
}
