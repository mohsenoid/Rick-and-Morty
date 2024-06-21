package com.mohsenoid.rickandmorty.util

import com.mohsenoid.rickandmorty.domain.episodes.model.Episode

fun createEpisodesList(count: Int): List<Episode> =
    buildList {
        repeat(count) {
            add(createEpisode(id = it))
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
