package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.api.model.ApiEpisode
import com.mohsenoid.rickandmorty.data.db.model.DbEpisode
import com.mohsenoid.rickandmorty.domain.model.ModelEpisode

private const val SEPARATOR: Char = '/'

internal fun extractCharacterIds(characters: List<String>): List<Int> {
    return characters.map { it.split(SEPARATOR).last().toInt() }
}

internal fun ApiEpisode.toDbEpisode() =
    DbEpisode(
        id = id,
        name = name,
        airDate = airDate,
        episode = episode,
        characterIds = extractCharacterIds(characters),
        url = url,
        created = created
    )

internal fun DbEpisode.toModelEpisode() =
    ModelEpisode(
        id = id,
        name = name,
        airDate = airDate,
        episode = episode,
        characterIds = characterIds,
        url = url,
        created = created
    )
