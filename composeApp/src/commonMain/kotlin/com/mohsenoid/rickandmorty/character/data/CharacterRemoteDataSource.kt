package com.mohsenoid.rickandmorty.character.data

import com.mohsenoid.rickandmorty.character.data.model.CharacterResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json

class CharacterRemoteDataSource {

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun fetchCharacter(characterId: Int): CharacterResponse =
        httpClient
            .get("https://rickandmortyapi.com/api/character/$characterId")
            .body<CharacterResponse>()

    fun close() {
        httpClient.close()
    }
}
