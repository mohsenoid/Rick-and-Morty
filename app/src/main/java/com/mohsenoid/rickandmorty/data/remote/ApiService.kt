package com.mohsenoid.rickandmorty.data.remote

import com.mohsenoid.rickandmorty.data.remote.model.CharacterRemoteModel
import com.mohsenoid.rickandmorty.data.remote.model.CharactersResponse
import com.mohsenoid.rickandmorty.data.remote.model.EpisodesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface ApiService {

    @GET("episode")
    suspend fun getEpisodes(@Query("page") page: Int): Response<EpisodesResponse>

    @GET("character/{characterIds}")
    suspend fun getCharacters(@Path("characterIds") characterIds: String): Response<CharactersResponse>

    @GET("character/{characterId}")
    suspend fun getCharacter(@Path("characterId") characterId: Int): Response<CharacterRemoteModel>
}
