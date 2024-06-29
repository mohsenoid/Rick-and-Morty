package com.mohsenoid.rickandmorty.domain.episodes

import com.mohsenoid.rickandmorty.domain.episodes.model.Episode

interface EpisodeRepository {
    suspend fun getEpisodes(page: Int = 0): Result<List<Episode>>
}
