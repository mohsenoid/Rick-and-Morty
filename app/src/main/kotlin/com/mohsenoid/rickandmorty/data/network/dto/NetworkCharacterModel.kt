package com.mohsenoid.rickandmorty.data.network.dto

import org.json.JSONArray
import org.json.JSONObject
import java.util.*

data class NetworkCharacterModel(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: NetworkOriginModel,
    val location: NetworkLocationModel,
    val image: String,
    val episodes: List<String>,
    val url: String,
    val created: String
) {

    companion object {
        private const val TAG_ID = "id"
        private const val TAG_NAME = "name"
        private const val TAG_STATUS = "status"
        private const val TAG_SPECIES = "species"
        private const val TAG_TYPE = "type"
        private const val TAG_GENDER = "gender"
        private const val TAG_ORIGIN = "origin"
        private const val TAG_LOCATION = "location"
        private const val TAG_IMAGE = "image"
        private const val TAG_EPISODE = "episode"
        private const val TAG_URL = "url"
        private const val TAG_CREATED = "created"

        fun fromJson(jsonArray: JSONArray): List<NetworkCharacterModel> {
            val characters = ArrayList<NetworkCharacterModel>()

            for (i in 0 until jsonArray.length()) {
                val characterJsonObject = jsonArray.getJSONObject(i)
                val character = fromJson(characterJsonObject)
                characters.add(character)
            }
            return characters
        }

        fun fromJson(json: String): NetworkCharacterModel {
            val jsonObject = JSONObject(json)
            return fromJson(jsonObject)
        }

        private fun fromJson(jsonObject: JSONObject): NetworkCharacterModel {
            val id = jsonObject.getInt(TAG_ID)
            val name = jsonObject.getString(TAG_NAME)
            val status = jsonObject.getString(TAG_STATUS)
            val species = jsonObject.getString(TAG_SPECIES)
            val type = jsonObject.getString(TAG_TYPE)
            val gender = jsonObject.getString(TAG_GENDER)
            val origin = NetworkOriginModel.fromJson(jsonObject.getJSONObject(TAG_ORIGIN))
            val location = NetworkLocationModel.fromJson(jsonObject.getJSONObject(TAG_LOCATION))
            val image = jsonObject.getString(TAG_IMAGE)
            val url = jsonObject.getString(TAG_URL)
            val created = jsonObject.getString(TAG_CREATED)
            val episodesJsonArray = jsonObject.getJSONArray(TAG_EPISODE)

            val episodes = ArrayList<String>()
            for (i in 0 until episodesJsonArray.length()) {
                val episode = episodesJsonArray.getString(i)
                episodes.add(episode)
            }

            return NetworkCharacterModel(
                id = id,
                name = name,
                status = status,
                species = species,
                type = type,
                gender = gender,
                origin = origin,
                location = location,
                image = image,
                episodes = episodes,
                url = url,
                created = created
            )
        }
    }
}
