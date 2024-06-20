package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.api.model.ApiEpisode
import com.mohsenoid.rickandmorty.data.db.model.DbEpisode
import com.mohsenoid.rickandmorty.domain.model.ModelEpisode
import org.junit.Test
import kotlin.test.assertEquals

class EpisodeMapperTest {

    @Test
    fun apiToDbEpisode() {
        // GIVEN
        val apiEpisode = ApiEpisode(
            id = EPISODE_ID,
            name = EPISODE_NAME,
            airDate = EPISODE_AIR_DATE,
            episode = EPISODE_EPISODE,
            characters = CHARACTER_CHARACTERS,
            url = EPISODE_URL,
            created = EPISODE_CREATED,
        )

        val expectedDbEpisode = DbEpisode(
            id = EPISODE_ID,
            name = EPISODE_NAME,
            airDate = EPISODE_AIR_DATE,
            episode = EPISODE_EPISODE,
            characterIds = CHARACTER_CHARACTER_IDS,
            url = EPISODE_URL,
            created = EPISODE_CREATED,
        )

        // WHEN
        val actualDbEpisode: DbEpisode = apiEpisode.toDbEpisode()

        // THEN
        assertEquals(expectedDbEpisode, actualDbEpisode)
    }

    @Test
    fun dbToModelEpisode() {
        // GIVEN
        val dbEpisode = DbEpisode(
            id = EPISODE_ID,
            name = EPISODE_NAME,
            airDate = EPISODE_AIR_DATE,
            episode = EPISODE_EPISODE,
            characterIds = CHARACTER_CHARACTER_IDS,
            url = EPISODE_URL,
            created = EPISODE_CREATED,
        )

        val expectedModelEpisode = ModelEpisode(
            id = EPISODE_ID,
            name = EPISODE_NAME,
            airDate = EPISODE_AIR_DATE,
            episode = EPISODE_EPISODE,
            characterIds = CHARACTER_CHARACTER_IDS,
            url = EPISODE_URL,
            created = EPISODE_CREATED,
        )

        // WHEN
        val actualModelEpisode: ModelEpisode = dbEpisode.toModelEpisode()

        // THEN
        assertEquals(expectedModelEpisode, actualModelEpisode)
    }

    companion object {
        private const val EPISODE_ID = 123
        private const val EPISODE_NAME = "some name"
        private const val EPISODE_AIR_DATE = "some air date"
        private const val EPISODE_EPISODE = "some episode"
        private val CHARACTER_CHARACTERS = listOf(
            "https://test.com/api/character/1",
            "https://test.com/api/character/2",
            "https://test.com/api/character/3",
        )
        private val CHARACTER_CHARACTER_IDS = listOf(1, 2, 3)
        private const val EPISODE_URL = "some url"
        private const val EPISODE_CREATED = "some created"
    }
}
