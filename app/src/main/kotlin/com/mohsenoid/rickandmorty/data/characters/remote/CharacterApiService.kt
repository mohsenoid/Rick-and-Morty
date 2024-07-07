package com.mohsenoid.rickandmorty.data.characters.remote

import com.mohsenoid.rickandmorty.data.characters.remote.model.CharacterRemoteModel
import com.mohsenoid.rickandmorty.data.characters.remote.model.CharactersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

internal interface CharacterApiService {
    @GET("character/{characterIds}")
    suspend fun getCharacters(
        @Path("characterIds") characterIds: String,
    ): Response<CharactersResponse>

    @GET("character/{characterId}")
    suspend fun getCharacter(
        @Path("characterId") characterId: Int,
    ): Response<CharacterRemoteModel>
}
