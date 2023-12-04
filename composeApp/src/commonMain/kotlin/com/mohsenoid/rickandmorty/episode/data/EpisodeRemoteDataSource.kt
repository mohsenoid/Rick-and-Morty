package com.mohsenoid.rickandmorty.episode.data

import com.mohsenoid.rickandmorty.character.data.model.CharacterResponse
import com.mohsenoid.rickandmorty.episode.data.model.EpisodeResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json

class EpisodeRemoteDataSource {

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun fetchEpisode(episodeId: Int): EpisodeResponse =
        httpClient
            .get("https://rickandmortyapi.com/api/episode/$episodeId")
            .body<EpisodeResponse>()

    suspend fun fetchCharacters(characterIds: List<Int>): List<CharacterResponse> =
        httpClient
            .get("https://rickandmortyapi.com/api/character/${characterIds.joinToString(",")}")
            .body<List<CharacterResponse>>()

    fun close() {
        httpClient.close()
    }
}
