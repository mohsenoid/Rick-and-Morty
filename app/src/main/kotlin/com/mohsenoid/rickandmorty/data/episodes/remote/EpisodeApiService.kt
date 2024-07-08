package com.mohsenoid.rickandmorty.data.episodes.remote

import com.mohsenoid.rickandmorty.data.episodes.remote.model.EpisodesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface EpisodeApiService {
    @GET("episode")
    suspend fun getEpisodes(
        @Query("page") page: Int,
    ): Response<EpisodesResponse>
}
