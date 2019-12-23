package com.mohsenoid.rickandmorty.test

import com.mohsenoid.rickandmorty.data.db.dto.DbEpisodeModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkEpisodeModel
import com.mohsenoid.rickandmorty.domain.entity.EpisodeEntity
import java.util.ArrayList

object EpisodeDataFactory {

    object Db {

        fun makeDbEpisodeModel(episodeId: Int = DataFactory.randomInt()): DbEpisodeModel {
            return DbEpisodeModel(
                id = episodeId,
                name = DataFactory.randomString(),
                airDate = DataFactory.randomString(),
                episode = DataFactory.randomString(),
                serializedCharacterIds = "${DataFactory.randomInt()},${DataFactory.randomInt()},${DataFactory.randomInt()}",
                url = DataFactory.randomString(),
                created = DataFactory.randomString()
            )
        }

        fun makeDbEpisodesModelList(count: Int): List<DbEpisodeModel> {
            val episodes: MutableList<DbEpisodeModel> =
                ArrayList()
            for (i in 0 until count) {
                val episode =
                    makeDbEpisodeModel()
                episodes.add(episode)
            }
            return episodes
        }
    }

    object Network {

        fun makeNetworkEpisodeModel(episodeId: Int = DataFactory.randomInt()): NetworkEpisodeModel {
            return NetworkEpisodeModel(
                episodeId,
                DataFactory.randomString(),
                DataFactory.randomString(),
                DataFactory.randomString(),
                DataFactory.randomStringList(5),
                DataFactory.randomString(),
                DataFactory.randomString()
            )
        }

        fun makeNetworkEpisodesModelList(count: Int): List<NetworkEpisodeModel> {
            val episodes: MutableList<NetworkEpisodeModel> =
                ArrayList()
            for (i in 0 until count) {
                val episode =
                    makeNetworkEpisodeModel()
                episodes.add(episode)
            }
            return episodes
        }
    }

    object Entity {

        fun makeEpisodeEntity(episodeId: Int = DataFactory.randomInt()): EpisodeEntity {
            return EpisodeEntity(
                episodeId,
                DataFactory.randomString(),
                DataFactory.randomString(),
                DataFactory.randomString(),
                DataFactory.randomIntList(5),
                DataFactory.randomString(),
                DataFactory.randomString()
            )
        }

        fun makeEpisodesEntityList(count: Int): List<EpisodeEntity> {
            val episodes: MutableList<EpisodeEntity> =
                ArrayList()
            for (i in 0 until count) {
                val episode =
                    makeEpisodeEntity()
                episodes.add(episode)
            }
            return episodes
        }
    }
}
