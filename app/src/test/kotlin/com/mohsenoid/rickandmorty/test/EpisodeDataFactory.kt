package com.mohsenoid.rickandmorty.test

import com.mohsenoid.rickandmorty.data.api.model.ApiEpisode
import com.mohsenoid.rickandmorty.data.db.model.DbEpisode
import com.mohsenoid.rickandmorty.domain.model.ModelEpisode

object EpisodeDataFactory {

    object Db {

        fun makeEpisode(episodeId: Int = DataFactory.randomInt()): DbEpisode {
            return DbEpisode(
                id = episodeId,
                name = DataFactory.randomString(),
                airDate = DataFactory.randomString(),
                episode = DataFactory.randomString(),
                characterIds = DataFactory.randomIntList(count = 5),
                url = DataFactory.randomString(),
                created = DataFactory.randomString()
            )
        }

        fun makeEpisodes(count: Int): List<DbEpisode> {
            val entityEpisodes: MutableList<DbEpisode> = ArrayList()
            for (i: Int in 0 until count) {
                val entityEpisode: DbEpisode = makeEpisode()
                entityEpisodes.add(entityEpisode)
            }
            return entityEpisodes
        }
    }

    object Network {

        fun makeEpisode(episodeId: Int = DataFactory.randomInt()): ApiEpisode {
            return ApiEpisode(
                id = episodeId,
                name = DataFactory.randomString(),
                airDate = DataFactory.randomString(),
                episode = DataFactory.randomString(),
                characters = DataFactory.randomIntList(count = 5).map { "${DataFactory.randomString()}/$it" },
                url = DataFactory.randomString(),
                created = DataFactory.randomString()
            )
        }

        fun makeEpisodes(count: Int): List<ApiEpisode> {
            val episodes: MutableList<ApiEpisode> = ArrayList()
            for (i: Int in 0 until count) {
                val episode: ApiEpisode = makeEpisode()
                episodes.add(episode)
            }
            return episodes
        }
    }

    object Entity {

        fun makeEpisode(episodeId: Int = DataFactory.randomInt()): ModelEpisode {
            return ModelEpisode(
                id = episodeId,
                name = DataFactory.randomString(),
                airDate = DataFactory.randomString(),
                episode = DataFactory.randomString(),
                characterIds = DataFactory.randomIntList(count = 5),
                url = DataFactory.randomString(),
                created = DataFactory.randomString()
            )
        }

        fun makeEpisodes(count: Int): List<ModelEpisode> {
            val episodes: MutableList<ModelEpisode> = ArrayList()
            for (i in 0 until count) {
                val episode: ModelEpisode = makeEpisode()
                episodes.add(episode)
            }
            return episodes
        }
    }
}
