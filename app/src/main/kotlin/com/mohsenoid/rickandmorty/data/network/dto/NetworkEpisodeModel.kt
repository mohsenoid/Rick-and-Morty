package com.mohsenoid.rickandmorty.data.network.dto

import org.json.JSONArray
import org.json.JSONObject
import java.util.*

data class NetworkEpisodeModel(
    val id: Int,
    val name: String,
    val airDate: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
) {

    companion object {
        private const val TAG_ID = "id"
        private const val TAG_NAME = "name"
        private const val TAG_AIR_DATE = "air_date"
        private const val TAG_EPISODE = "episode"
        private const val TAG_CHARACTERS = "characters"
        private const val TAG_URL = "url"
        private const val TAG_CREATED = "created"

        fun fromJson(jsonArray: JSONArray): List<NetworkEpisodeModel> {
            val episodes = ArrayList<NetworkEpisodeModel>()

            for (i in 0 until jsonArray.length()) {
                val episodeJsonObject = jsonArray.getJSONObject(i)
                val episode = fromJson(episodeJsonObject)
                episodes.add(episode)
            }

            return episodes
        }


        private fun fromJson(jsonObject: JSONObject): NetworkEpisodeModel {
            val id = jsonObject.getInt(TAG_ID)
            val name = jsonObject.getString(TAG_NAME)
            val airDate = jsonObject.getString(TAG_AIR_DATE)
            val episode = jsonObject.getString(TAG_EPISODE)
            val url = jsonObject.getString(TAG_URL)
            val created = jsonObject.getString(TAG_CREATED)
            val charactersJsonArray = jsonObject.getJSONArray(TAG_CHARACTERS)

            val characters = ArrayList<String>()
            for (i in 0 until charactersJsonArray.length()) {
                val character = charactersJsonArray.getString(i)
                characters.add(character)
            }

            return NetworkEpisodeModel(
                id = id,
                name = name,
                airDate = airDate,
                episode = episode,
                characters = characters,
                url = url,
                created = created
            )
        }
    }
}
