package com.mohsenoid.rickandmorty.data.network.dto

import org.json.JSONObject

data class NetworkLocationModel(
    val name: String,
    val url: String
) {

    companion object {
        private const val TAG_NAME = "name"
        private const val TAG_URL = "url"

        fun fromJson(jsonObject: JSONObject): NetworkLocationModel {
            val name = jsonObject.getString(TAG_NAME)
            val url = jsonObject.getString(TAG_URL)

            return NetworkLocationModel(
                name = name,
                url = url
            )
        }
    }
}
