package com.mohsenoid.rickandmorty.domain.episodes

import com.mohsenoid.rickandmorty.domain.RepositoryGetResult
import com.mohsenoid.rickandmorty.domain.episodes.model.Episode

interface EpisodesRepository {
    suspend fun getEpisodes(page: Int = 0): RepositoryGetResult<List<Episode>>
}
