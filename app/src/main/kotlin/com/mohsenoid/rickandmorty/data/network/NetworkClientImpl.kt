package com.mohsenoid.rickandmorty.data.network

import com.mohsenoid.rickandmorty.data.Serializer.serializeIntegerList
import com.mohsenoid.rickandmorty.data.network.dto.NetworkCharacterModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkCharactersResponse
import com.mohsenoid.rickandmorty.data.network.dto.NetworkEpisodeModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkEpisodesResponse
import java.util.*

class NetworkClientImpl(private val networkHelper: NetworkHelper) : NetworkClient {

    override fun getEpisodes(page: Int): List<NetworkEpisodeModel> {
        val params = ArrayList<NetworkHelper.Param>()
        params.add(NetworkHelper.Param(NetworkConstants.PARAM_KEY_PAGE, page.toString()))

        val jsonResponse = networkHelper.requestData(NetworkConstants.EPISODE_ENDPOINT, params)
        val episodesResponse = NetworkEpisodesResponse.fromJson(jsonResponse)
        return episodesResponse.results
    }

    override fun getCharactersByIds(characterIds: List<Int>): List<NetworkCharacterModel> {
        if (characterIds.isEmpty()) return emptyList()

        val characterEndpoint =
            "${NetworkConstants.CHARACTER_ENDPOINT}[${serializeIntegerList(characterIds)}]"

        val jsonResponse = networkHelper.requestData(characterEndpoint, null)
        val charactersResponse = NetworkCharactersResponse.fromJson(jsonResponse)
        return charactersResponse.results
    }

    override fun getCharacterDetails(characterId: Int): NetworkCharacterModel {
        val characterEndpoint = NetworkConstants.CHARACTER_ENDPOINT + characterId
        val jsonResponse = networkHelper.requestData(characterEndpoint, null)
        return NetworkCharacterModel.fromJson(jsonResponse)
    }
}
