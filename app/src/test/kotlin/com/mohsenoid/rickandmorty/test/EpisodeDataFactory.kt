package com.mohsenoid.rickandmorty.test

import com.mohsenoid.rickandmorty.data.db.dto.DbEpisodeModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkEpisodeModel
import com.mohsenoid.rickandmorty.domain.entity.EpisodeEntity

object EpisodeDataFactory {

    object Db {

        fun makeEpisode(episodeId: Int = DataFactory.randomInt()): DbEpisodeModel {
            return DbEpisodeModel(
                id = episodeId,
                name = DataFactory.randomString(),
                airDate = DataFactory.randomString(),
                episode = DataFactory.randomString(),
                characterIds = DataFactory.randomIntList(count = 5),
                url = DataFactory.randomString(),
                created = DataFactory.randomString()
            )
        }

        fun makeEpisodes(count: Int): List<DbEpisodeModel> {
            val episodes: MutableList<DbEpisodeModel> = ArrayList()
            for (i: Int in 0 until count) {
                val episode: DbEpisodeModel = makeEpisode()
                episodes.add(episode)
            }
            return episodes
        }
    }

    object Network {

        fun makeEpisode(episodeId: Int = DataFactory.randomInt()): NetworkEpisodeModel {
            return NetworkEpisodeModel(
                id = episodeId,
                name = DataFactory.randomString(),
                airDate = DataFactory.randomString(),
                episode = DataFactory.randomString(),
                characters = DataFactory.randomIntList(count = 5).map { "${DataFactory.randomString()}/$it" },
                url = DataFactory.randomString(),
                created = DataFactory.randomString()
            )
        }

        fun makeEpisodes(count: Int): List<NetworkEpisodeModel> {
            val episodes: MutableList<NetworkEpisodeModel> = ArrayList()
            for (i: Int in 0 until count) {
                val episode: NetworkEpisodeModel = makeEpisode()
                episodes.add(episode)
            }
            return episodes
        }
    }

    object Entity {

        fun makeEpisode(episodeId: Int = DataFactory.randomInt()): EpisodeEntity {
            return EpisodeEntity(
                id = episodeId,
                name = DataFactory.randomString(),
                airDate = DataFactory.randomString(),
                episode = DataFactory.randomString(),
                characterIds = DataFactory.randomIntList(count = 5),
                url = DataFactory.randomString(),
                created = DataFactory.randomString()
            )
        }

        fun makeEpisodesList(count: Int): List<EpisodeEntity> {
            val episodes: MutableList<EpisodeEntity> = ArrayList()
            for (i in 0 until count) {
                val episode: EpisodeEntity = makeEpisode()
                episodes.add(episode)
            }
            return episodes
        }
    }
}
