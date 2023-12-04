package com.mohsenoid.rickandmorty.episodes.data

import com.mohsenoid.rickandmorty.episodes.domain.EpisodesRepository
import com.mohsenoid.rickandmorty.episodes.domain.model.Episode

internal class EpisodesRepositoryImpl(
    private val remote: EpisodesRemoteDataSource,
) : EpisodesRepository {

    override suspend fun getEpisodes(page: Int): List<Episode> {
        return remote.fetchEpisodes(page).episodesResponseResults.map {
            Episode(
                id = it.id,
                name = it.name,
                airDate = it.airDate,
                episode = it.episode,
                characters = it.characters,
                created = it.created,
            )
        }
    }

    override fun close() {
        remote.close()
    }
}
