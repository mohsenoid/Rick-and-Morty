package com.mohsenoid.rickandmorty.data.network

import com.mohsenoid.rickandmorty.data.network.dto.NetworkCharacterModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkEpisodesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkClient {

    @GET(value = NetworkConstants.EPISODE_ENDPOINT)
    suspend fun fetchEpisodes(
        @Query(value = NetworkConstants.PARAM_KEY_PAGE)
        page: Int
    ): Response<NetworkEpisodesResponse>

    @GET(value = "${NetworkConstants.CHARACTER_ENDPOINT}{${NetworkConstants.PATH_KEY_CHARACTER_IDS}}")
    suspend fun fetchCharactersByIds(
        @Path(value = NetworkConstants.PATH_KEY_CHARACTER_IDS)
        characterIds: List<Int>
    ): Response<List<NetworkCharacterModel>>

    @GET(value = "${NetworkConstants.CHARACTER_ENDPOINT}{${NetworkConstants.PATH_KEY_CHARACTER_ID}}")
    suspend fun fetchCharacterDetails(
        @Path(value = NetworkConstants.PATH_KEY_CHARACTER_ID)
        characterId: Int
    ): Response<NetworkCharacterModel>
}
