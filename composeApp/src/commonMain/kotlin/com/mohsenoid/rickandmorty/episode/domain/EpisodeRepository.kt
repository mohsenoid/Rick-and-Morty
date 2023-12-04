package com.mohsenoid.rickandmorty.episode.domain

import com.mohsenoid.rickandmorty.episode.domain.model.Episode

interface EpisodeRepository {
    suspend fun getEpisode(episodeId: Int): Episode
    fun close()
}
