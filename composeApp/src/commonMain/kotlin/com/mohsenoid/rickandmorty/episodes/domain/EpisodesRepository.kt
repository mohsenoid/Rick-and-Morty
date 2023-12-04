package com.mohsenoid.rickandmorty.episodes.domain

import com.mohsenoid.rickandmorty.episodes.domain.model.Episode

interface EpisodesRepository {
    suspend fun getEpisodes(page: Int): List<Episode>
    fun close()
}
