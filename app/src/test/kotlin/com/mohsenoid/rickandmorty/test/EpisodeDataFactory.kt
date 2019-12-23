package com.mohsenoid.rickandmorty.test

import com.mohsenoid.rickandmorty.data.db.dto.DbEpisodeModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkEpisodeModel
import com.mohsenoid.rickandmorty.domain.entity.EpisodeEntity
import com.mohsenoid.rickandmorty.test.DataFactory.randomInt
import com.mohsenoid.rickandmorty.test.DataFactory.randomIntList
import com.mohsenoid.rickandmorty.test.DataFactory.randomString
import com.mohsenoid.rickandmorty.test.DataFactory.randomStringList
import java.util.ArrayList

object EpisodeDataFactory {

    object Db {

        fun makeDbEpisodeModel(episodeId: Int = randomInt()): DbEpisodeModel {
            return DbEpisodeModel(
                id = episodeId,
                name = randomString(),
                airDate = randomString(),
                episode = randomString(),
                serializedCharacterIds = randomString() + "," + randomString() + "," + randomString(),
                url = randomString(),
                created = randomString()
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

        fun makeNetworkEpisodeModel(episodeId: Int = randomInt()): NetworkEpisodeModel {
            return NetworkEpisodeModel(
                episodeId,
                randomString(),
                randomString(),
                randomString(),
                randomStringList(5),
                randomString(),
                randomString()
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

        fun makeEpisodeEntity(episodeId: Int = randomInt()): EpisodeEntity {
            return EpisodeEntity(
                episodeId,
                randomString(),
                randomString(),
                randomString(),
                randomIntList(5),
                randomString(),
                randomString()
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
