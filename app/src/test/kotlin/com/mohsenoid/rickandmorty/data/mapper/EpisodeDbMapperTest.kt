package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.entity.DbEntityEpisode
import com.mohsenoid.rickandmorty.data.network.dto.NetworkEpisodeModel
import com.mohsenoid.rickandmorty.test.EpisodeDataFactory
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class EpisodeDbMapperTest {

    private lateinit var episodeDbEntityMapper: Mapper<NetworkEpisodeModel, DbEntityEpisode>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        episodeDbEntityMapper = EpisodeDbMapper()
    }

    @Test
    fun map() {
        // GIVEN
        val networkEpisode: NetworkEpisodeModel = EpisodeDataFactory.Network.makeEpisode()

        val expectedEpisode = DbEntityEpisode(
            id = networkEpisode.id,
            name = networkEpisode.name,
            airDate = networkEpisode.airDate,
            episode = networkEpisode.episode,
            characterIds = EpisodeDbMapper.extractCharacterIds(networkEpisode.characters),
            url = networkEpisode.url,
            created = networkEpisode.created
        )

        // WHEN
        val actualEntityEpisode: DbEntityEpisode = episodeDbEntityMapper.map(networkEpisode)

        // THEN
        expectedEpisode shouldEqual actualEntityEpisode
    }
}
