package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.dto.DbEpisodeModel
import com.mohsenoid.rickandmorty.domain.entity.EpisodeEntity
import com.mohsenoid.rickandmorty.test.EpisodeDataFactory
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class EpisodeEntityMapperTest {

    private lateinit var episodeEntityMapper: Mapper<DbEpisodeModel, EpisodeEntity>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        episodeEntityMapper = EpisodeEntityMapper()
    }

    @Test
    fun map() {
        // GIVEN
        val dbEpisode: DbEpisodeModel = EpisodeDataFactory.Db.makeEpisode()

        val expectedEpisode = EpisodeEntity(
            id = dbEpisode.id,
            name = dbEpisode.name,
            airDate = dbEpisode.airDate,
            episode = dbEpisode.episode,
            characterIds = dbEpisode.characterIds,
            url = dbEpisode.url,
            created = dbEpisode.created
        )

        // WHEN
        val actualEpisode: EpisodeEntity = episodeEntityMapper.map(dbEpisode)

        // THEN
        expectedEpisode shouldEqual actualEpisode
    }
}
