package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.model.DbEpisode
import com.mohsenoid.rickandmorty.domain.model.ModelEpisode
import com.mohsenoid.rickandmorty.test.EpisodeDataFactory
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class EpisodeEntityMapperTest {

    private lateinit var entityEpisodeEntityMapper: Mapper<DbEpisode, ModelEpisode>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        entityEpisodeEntityMapper = EpisodeEntityMapper()
    }

    @Test
    fun map() {
        // GIVEN
        val dbEntityEpisode: DbEpisode = EpisodeDataFactory.Db.makeEpisode()

        val expectedEpisode = ModelEpisode(
            id = dbEntityEpisode.id,
            name = dbEntityEpisode.name,
            airDate = dbEntityEpisode.airDate,
            episode = dbEntityEpisode.episode,
            characterIds = dbEntityEpisode.characterIds,
            url = dbEntityEpisode.url,
            created = dbEntityEpisode.created
        )

        // WHEN
        val actualEpisode: ModelEpisode = entityEpisodeEntityMapper.map(dbEntityEpisode)

        // THEN
        expectedEpisode shouldEqual actualEpisode
    }
}
