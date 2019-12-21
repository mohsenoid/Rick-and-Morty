package com.mohsenoid.rickandmorty.data.network

import com.mohsenoid.rickandmorty.data.network.dto.NetworkCharacterModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkEpisodeModel

interface NetworkClient {

    fun getEpisodes(page: Int): List<NetworkEpisodeModel>

    fun getCharactersByIds(characterIds: List<Int>): List<NetworkCharacterModel>

    fun getCharacterDetails(characterId: Int): NetworkCharacterModel
}
