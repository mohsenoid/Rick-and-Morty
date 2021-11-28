package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.api.model.ApiCharacter
import com.mohsenoid.rickandmorty.data.api.model.ApiLocation
import com.mohsenoid.rickandmorty.data.api.model.ApiOrigin
import com.mohsenoid.rickandmorty.data.db.model.DbCharacter
import com.mohsenoid.rickandmorty.data.db.model.DbLocation
import com.mohsenoid.rickandmorty.data.db.model.DbOrigin
import com.mohsenoid.rickandmorty.domain.model.ModelCharacter
import com.mohsenoid.rickandmorty.domain.model.ModelLocation
import com.mohsenoid.rickandmorty.domain.model.ModelOrigin

private const val DEAD: String = "dead"

private const val SEPARATOR: Char = '/'

internal fun extractEpisodeIds(episodes: List<String>): List<Int> {
    return episodes.map { it.split(SEPARATOR).last().toInt() }
}

internal fun ApiCharacter.toDbCharacter() =
    DbCharacter(
        id = id,
        name = name,
        status = status,
        isAlive = !status.equals(DEAD, ignoreCase = true),
        species = species,
        type = type,
        gender = gender,
        origin = origin.toDbOrigin(),
        location = location.toDbLocation(),
        image = image,
        episodeIds = extractEpisodeIds(episodes),
        url = url,
        created = created,
        isKilledByUser = false
    )

private fun ApiOrigin.toDbOrigin() =
    DbOrigin(
        name = name,
        url = url
    )

private fun ApiLocation.toDbLocation() =
    DbLocation(
        name = name,
        url = url
    )

internal fun DbCharacter.toModelCharacter() =
    ModelCharacter(
        id = id,
        name = name,
        status = status,
        isAlive = isAlive,
        species = species,
        type = type,
        gender = gender,
        origin = origin.toModelOrigin(),
        location = location.toModelLocation(),
        imageUrl = image,
        episodeIds = episodeIds,
        url = url,
        created = created,
        isKilledByUser = isKilledByUser
    )

private fun DbOrigin.toModelOrigin() =
    ModelOrigin(
        name = name,
        url = url
    )

private fun DbLocation.toModelLocation() =
    ModelLocation(
        name = name,
        url = url
    )
