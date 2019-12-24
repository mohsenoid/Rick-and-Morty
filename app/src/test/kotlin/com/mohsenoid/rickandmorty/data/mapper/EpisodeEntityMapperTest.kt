package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.dto.DbEpisodeModel
import com.mohsenoid.rickandmorty.domain.entity.EpisodeEntity
import com.mohsenoid.rickandmorty.test.EpisodeDataFactory
import com.mohsenoid.rickandmorty.util.extension.deserializeStringList
import com.mohsenoid.rickandmorty.util.extension.mapStringListToIntegerList
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class EpisodeEntityMapperTest {

    lateinit var episodeEntityMapper: Mapper<DbEpisodeModel, EpisodeEntity>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        episodeEntityMapper = EpisodeEntityMapper()
    }

    @Test
    fun map() {
        // GIVEN
        val dbEpisode = EpisodeDataFactory.Db.makeDbEpisodeModel()

        val expectedEpisode = EpisodeEntity(
            id = dbEpisode.id,
            name = dbEpisode.name,
            airDate = dbEpisode.airDate,
            episode = dbEpisode.episode,
            characterIds = dbEpisode.serializedCharacterIds.deserializeStringList().mapStringListToIntegerList(),
            url = dbEpisode.url,
            created = dbEpisode.created
        )

        // WHEN
        val actualEpisode = episodeEntityMapper.map(dbEpisode)

        // THEN
        expectedEpisode shouldEqual actualEpisode
    }
}
