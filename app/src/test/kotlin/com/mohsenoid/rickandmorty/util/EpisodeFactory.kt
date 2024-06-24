package com.mohsenoid.rickandmorty.util

import com.mohsenoid.rickandmorty.domain.episodes.model.Episode

fun createEpisodesList(
    count: Int,
    id: (Int) -> Int = { it },
    name: (Int) -> String = { "name$it" },
    airDate: (Int) -> String = { "airDate$it" },
    episode: (Int) -> String = { "episode$it" },
    characters: (Int) -> Set<Int> = { emptySet() },
): List<Episode> =
    buildList {
        repeat(count) { index ->
            val newEpisode =
                createEpisode(
                    id = id(index),
                    name = name(index),
                    airDate = airDate(index),
                    episode = episode(index),
                    characters = characters(index),
                )
            add(newEpisode)
        }
    }

fun createEpisode(
    id: Int = 0,
    name: String = "name",
    airDate: String = "airDate",
    episode: String = "episode",
    characters: Set<Int> = emptySet(),
): Episode =
    Episode(
        id = id,
        name = name,
        airDate = airDate,
        episode = episode,
        characters = characters,
    )
