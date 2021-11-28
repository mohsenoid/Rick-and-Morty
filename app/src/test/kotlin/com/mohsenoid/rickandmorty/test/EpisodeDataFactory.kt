package com.mohsenoid.rickandmorty.test

import com.mohsenoid.rickandmorty.data.api.model.ApiEpisode
import com.mohsenoid.rickandmorty.data.db.model.DbEpisode
import com.mohsenoid.rickandmorty.domain.model.ModelEpisode

object EpisodeDataFactory {

    internal fun makeDbEpisode(episodeId: Int = DataFactory.randomInt()): DbEpisode =
        DbEpisode(
            id = episodeId,
            name = DataFactory.randomString(),
            airDate = DataFactory.randomString(),
            episode = DataFactory.randomString(),
            characterIds = DataFactory.randomIntList(count = 5),
            url = DataFactory.randomString(),
            created = DataFactory.randomString(),
        )

    internal fun makeDbEpisodes(count: Int): List<DbEpisode> {
        val entityEpisodes: MutableList<DbEpisode> = ArrayList()
        repeat(count) {
            val entityEpisode: DbEpisode = makeDbEpisode()
            entityEpisodes.add(entityEpisode)
        }
        return entityEpisodes
    }

    internal fun makeApiEpisode(episodeId: Int = DataFactory.randomInt()): ApiEpisode =
        ApiEpisode(
            id = episodeId,
            name = DataFactory.randomString(),
            airDate = DataFactory.randomString(),
            episode = DataFactory.randomString(),
            characters = DataFactory.randomIntList(count = 5)
                .map { "${DataFactory.randomString()}/$it" },
            url = DataFactory.randomString(),
            created = DataFactory.randomString(),
        )

    internal fun makeApiEpisodes(count: Int): List<ApiEpisode> {
        val episodes: MutableList<ApiEpisode> = ArrayList()
        repeat(count) {
            val episode: ApiEpisode = makeApiEpisode()
            episodes.add(episode)
        }
        return episodes
    }

    internal fun makeEpisode(episodeId: Int = DataFactory.randomInt()): ModelEpisode =
        ModelEpisode(
            id = episodeId,
            name = DataFactory.randomString(),
            airDate = DataFactory.randomString(),
            episode = DataFactory.randomString(),
            characterIds = DataFactory.randomIntList(count = 5),
            url = DataFactory.randomString(),
            created = DataFactory.randomString(),
        )

    internal fun makeEpisodes(count: Int): List<ModelEpisode> {
        val episodes: MutableList<ModelEpisode> = ArrayList()
        repeat(count) {
            val episode: ModelEpisode = makeEpisode()
            episodes.add(episode)
        }
        return episodes
    }
}
