package com.mohsenoid.rickandmorty.util

import com.mohsenoid.rickandmorty.data.episodes.entity.EpisodeEntity

internal fun createEpisodesEntityList(
    count: Int,
    id: (Int) -> Int = { it },
    page: (Int) -> Int = { 0 },
    name: (Int) -> String = { "name$it" },
    airDate: (Int) -> String = { "airDate$it" },
    episode: (Int) -> String = { "episode$it" },
    characters: (Int) -> Set<Int> = { emptySet() },
): List<EpisodeEntity> =
    buildList {
        repeat(count) { index ->
            val newEpisodeEntity =
                createEpisodeEntity(
                    id = id(index),
                    page = page(index),
                    name = name(index),
                    airDate = airDate(index),
                    episode = episode(index),
                    characters = characters(index),
                )
            add(newEpisodeEntity)
        }
    }

internal fun createEpisodeEntity(
    id: Int = 0,
    page: Int = 0,
    name: String = "name",
    airDate: String = "airDate",
    episode: String = "episode",
    characters: Set<Int> = emptySet(),
): EpisodeEntity =
    EpisodeEntity(
        id = id,
        page = page,
        name = name,
        airDate = airDate,
        episode = episode,
        characters = characters,
    )
