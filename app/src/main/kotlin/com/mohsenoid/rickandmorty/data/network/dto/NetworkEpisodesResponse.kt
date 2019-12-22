package com.mohsenoid.rickandmorty.data.network.dto

import org.json.JSONObject

data class NetworkEpisodesResponse(
    val info: NetworkInfoModel,
    val results: List<NetworkEpisodeModel>
) {

    companion object {
        private const val TAG_INFO = "info"
        private const val TAG_RESULTS = "results"

        fun fromJson(json: String): NetworkEpisodesResponse {
            val jsonObject = JSONObject(json)
            val infoJsonObject = jsonObject.getJSONObject(TAG_INFO)
            val resultsJsonArray = jsonObject.getJSONArray(TAG_RESULTS)
            val info = NetworkInfoModel.fromJson(infoJsonObject)
            val results = NetworkEpisodeModel.fromJson(resultsJsonArray)

            return NetworkEpisodesResponse(
                info = info,
                results = results
            )
        }
    }
}
