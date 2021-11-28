package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.api.model.ApiEpisode
import com.mohsenoid.rickandmorty.data.db.model.DbEpisode
import com.mohsenoid.rickandmorty.test.EpisodeDataFactory
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class EpisodeDbMapperTest {

    private lateinit var episodeDbEntityMapper: Mapper<ApiEpisode, DbEpisode>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        episodeDbEntityMapper = EpisodeDbMapper()
    }

    @Test
    fun map() {
        // GIVEN
        val apiEpisode: ApiEpisode = EpisodeDataFactory.Network.makeEpisode()

        val expectedEpisode = DbEpisode(
            id = apiEpisode.id,
            name = apiEpisode.name,
            airDate = apiEpisode.airDate,
            episode = apiEpisode.episode,
            characterIds = EpisodeDbMapper.extractCharacterIds(apiEpisode.characters),
            url = apiEpisode.url,
            created = apiEpisode.created
        )

        // WHEN
        val actualEntityEpisode: DbEpisode = episodeDbEntityMapper.map(apiEpisode)

        // THEN
        expectedEpisode shouldEqual actualEntityEpisode
    }
}
