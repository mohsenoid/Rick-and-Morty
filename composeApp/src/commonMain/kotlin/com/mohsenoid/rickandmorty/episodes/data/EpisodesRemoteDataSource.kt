package com.mohsenoid.rickandmorty.episodes.data

import com.mohsenoid.rickandmorty.episodes.data.model.EpisodesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json

class EpisodesRemoteDataSource {

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun fetchEpisodes(page: Int): EpisodesResponse =
        httpClient
            .get("https://rickandmortyapi.com/api/episode") {
                parameter("page", page)
            }
            .body<EpisodesResponse>()

    fun close() {
        httpClient.close()
    }
}
