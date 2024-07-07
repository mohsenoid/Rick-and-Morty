package com.mohsenoid.rickandmorty.data.episodes.mapper

import com.mohsenoid.rickandmorty.data.episodes.db.entity.EpisodeEntity
import com.mohsenoid.rickandmorty.data.episodes.remote.model.EpisodeRemoteModel
import com.mohsenoid.rickandmorty.domain.episodes.model.Episode

internal object EpisodeMapper {
    fun EpisodeRemoteModel.toEpisodeEntity(page: Int) =
        EpisodeEntity(
            id = id,
            page = page,
            name = name,
            airDate = airDate,
            episode = episode,
            characters = characters.toCharacterIds(),
        )

    fun EpisodeEntity.toEpisode() =
        Episode(
            id = id,
            name = name,
            airDate = airDate,
            episode = episode,
            characters = characters,
        )
}

private fun Set<String>.toCharacterIds(): Set<Int> = mapNotNull { it.split("/").lastOrNull()?.toIntOrNull() }.toSet()
