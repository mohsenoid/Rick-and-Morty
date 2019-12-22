package com.mohsenoid.rickandmorty.data.network

import com.mohsenoid.rickandmorty.data.network.dto.NetworkCharacterModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkEpisodesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkClient {

    @GET(NetworkConstants.EPISODE_ENDPOINT)
    suspend fun getEpisodes(
        @Query(NetworkConstants.PARAM_KEY_PAGE)
        page: Int
    ): Response<NetworkEpisodesResponse>

    @GET("${NetworkConstants.CHARACTER_ENDPOINT}{${NetworkConstants.PATH_KEY_CHARACTER_IDS}}")
    suspend fun getCharactersByIds(
        @Path(NetworkConstants.PATH_KEY_CHARACTER_IDS)
        characterIds: List<Int>
    ): Response<List<NetworkCharacterModel>>

    @GET("${NetworkConstants.CHARACTER_ENDPOINT}{${NetworkConstants.PATH_KEY_CHARACTER_ID}}")
    suspend fun getCharacterDetails(
        @Path(NetworkConstants.PATH_KEY_CHARACTER_ID)
        characterId: Int
    ): Response<NetworkCharacterModel>
}
