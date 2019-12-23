package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.Serializer
import com.mohsenoid.rickandmorty.data.db.dto.DbEpisodeModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkEpisodeModel
import com.mohsenoid.rickandmorty.test.EpisodeDataFactory
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class EpisodeDbMapperTest {

    lateinit var episodeDbMapper: Mapper<NetworkEpisodeModel, DbEpisodeModel>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        episodeDbMapper = EpisodeDbMapper()
    }

    @Test
    fun map() {
        // GIVEN
        val networkEpisode = EpisodeDataFactory.Network.makeNetworkEpisodeModel()

        val expected = DbEpisodeModel(
            id = networkEpisode.id,
            name = networkEpisode.name,
            airDate = networkEpisode.airDate,
            episode = networkEpisode.episode,
            serializedCharacterIds = Serializer.serializeStringList(
                EpisodeDbMapper.getCharacterIds(
                    networkEpisode.characters
                )
            ),
            url = networkEpisode.url,
            created = networkEpisode.created
        )

        // WHEN
        val actual = episodeDbMapper.map(networkEpisode)

        // THEN
        Assert.assertEquals(expected, actual)
    }
}
