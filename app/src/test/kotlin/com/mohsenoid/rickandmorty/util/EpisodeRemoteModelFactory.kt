package com.mohsenoid.rickandmorty.util

import com.mohsenoid.rickandmorty.data.episodes.remote.model.EpisodeRemoteModel
import com.mohsenoid.rickandmorty.data.episodes.remote.model.EpisodesResponse
import com.mohsenoid.rickandmorty.data.episodes.remote.model.InfoRemoteModel

internal fun createEpisodeResponse(
    info: InfoRemoteModel = createInfoRemoteModel(),
    results: List<EpisodeRemoteModel> = createEpisodesRemoteModelList(3),
) = EpisodesResponse(
    info = info,
    results = results,
)

internal fun createInfoRemoteModel(
    count: Int = 1,
    pages: Int = 1,
    next: String? = null,
    prev: String? = null,
) = InfoRemoteModel(
    count = count,
    pages = pages,
    next = next,
    prev = prev,
)

internal fun createEpisodesRemoteModelList(
    count: Int,
    id: (Int) -> Int = { it },
    name: (Int) -> String = { "name$it" },
    airDate: (Int) -> String = { "airDate$it" },
    episode: (Int) -> String = { "episode$it" },
    characters: (Int) -> Set<String> = { emptySet() },
    created: (Int) -> String = { "created$it" },
    url: (Int) -> String = { "https://rickandmortyapi.com/api/character/$it" },
): List<EpisodeRemoteModel> =
    buildList {
        repeat(count) { index ->
            val newEpisodeRemoteModel =
                EpisodeRemoteModel(
                    id = id(index),
                    name = name(index),
                    airDate = airDate(index),
                    episode = episode(index),
                    characters = characters(index),
                    created = created(index),
                    url = url(index),
                )
            add(newEpisodeRemoteModel)
        }
    }

internal fun createEpisodeRemoteModel(
    id: Int = 1,
    name: String = "name",
    airDate: String = "airDate",
    episode: String = "episode",
    characters: Set<String> = emptySet(),
    url: String = "url",
    created: String = "created",
) = EpisodeRemoteModel(
    id = id,
    name = name,
    airDate = airDate,
    episode = episode,
    characters = characters,
    created = created,
    url = url,
)
