package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.api.model.ApiCharacter
import com.mohsenoid.rickandmorty.data.api.model.ApiLocation
import com.mohsenoid.rickandmorty.data.api.model.ApiOrigin
import com.mohsenoid.rickandmorty.data.db.model.DbCharacter
import com.mohsenoid.rickandmorty.data.db.model.DbLocation
import com.mohsenoid.rickandmorty.data.db.model.DbOrigin
import com.mohsenoid.rickandmorty.domain.model.ModelCharacter
import com.mohsenoid.rickandmorty.domain.model.ModelLocation
import com.mohsenoid.rickandmorty.domain.model.ModelOrigin
import org.junit.Test
import kotlin.test.assertEquals

class CharacterMapperTest {

    @Test
    fun modelToDbCharacter() {
        // GIVEN
        val apiCharacter = ApiCharacter(
            id = CHARACTER_ID,
            name = CHARACTER_NAME,
            status = CHARACTER_STATUS,
            species = CHARACTER_SPECIES,
            type = CHARACTER_TYPE,
            gender = CHARACTER_GENDER,
            origin = ApiOrigin(CHARACTER_ORIGIN_NAME, CHARACTER_ORIGIN_URL),
            location = ApiLocation(CHARACTER_LOCATION_NAME, CHARACTER_LOCATION_URL),
            image = CHARACTER_IMAGE,
            episodes = CHARACTER_EPISODES,
            url = CHARACTER_URL,
            created = CHARACTER_CREATED,
        )

        val expectedDbCharacter = DbCharacter(
            id = CHARACTER_ID,
            name = CHARACTER_NAME,
            status = CHARACTER_STATUS,
            isAlive = CHARACTER_STATUS_ALIVE,
            species = CHARACTER_SPECIES,
            type = CHARACTER_TYPE,
            gender = CHARACTER_GENDER,
            origin = DbOrigin(CHARACTER_ORIGIN_NAME, CHARACTER_ORIGIN_URL),
            location = DbLocation(CHARACTER_LOCATION_NAME, CHARACTER_LOCATION_URL),
            image = CHARACTER_IMAGE,
            episodeIds = CHARACTER_EPISODE_IDS,
            url = CHARACTER_URL,
            created = CHARACTER_CREATED,
            isKilledByUser = false,
        )

        // WHEN
        val actualDbCharacter: DbCharacter = apiCharacter.toDbCharacter()

        // THEN
        assertEquals(expectedDbCharacter, actualDbCharacter)
    }

    @Test
    fun dbToModelCharacter() {
        // GIVEN
        val dbCharacter = DbCharacter(
            id = CHARACTER_ID,
            name = CHARACTER_NAME,
            status = CHARACTER_STATUS,
            isAlive = CHARACTER_STATUS_ALIVE,
            species = CHARACTER_SPECIES,
            type = CHARACTER_TYPE,
            gender = CHARACTER_GENDER,
            origin = DbOrigin(CHARACTER_ORIGIN_NAME, CHARACTER_ORIGIN_URL),
            location = DbLocation(CHARACTER_LOCATION_NAME, CHARACTER_LOCATION_URL),
            image = CHARACTER_IMAGE,
            episodeIds = CHARACTER_EPISODE_IDS,
            url = CHARACTER_URL,
            created = CHARACTER_CREATED,
            isKilledByUser = CHARACTER_IS_KILLED_BY_USER,
        )

        val expectedModelCharacter = ModelCharacter(
            id = CHARACTER_ID,
            name = CHARACTER_NAME,
            status = CHARACTER_STATUS,
            isAlive = CHARACTER_STATUS_ALIVE,
            species = CHARACTER_SPECIES,
            type = CHARACTER_TYPE,
            gender = CHARACTER_GENDER,
            origin = ModelOrigin(CHARACTER_ORIGIN_NAME, CHARACTER_ORIGIN_URL),
            location = ModelLocation(CHARACTER_LOCATION_NAME, CHARACTER_LOCATION_URL),
            imageUrl = CHARACTER_IMAGE,
            episodeIds = CHARACTER_EPISODE_IDS,
            url = CHARACTER_URL,
            created = CHARACTER_CREATED,
            isKilledByUser = CHARACTER_IS_KILLED_BY_USER,
        )

        // WHEN
        val actualModelCharacter: ModelCharacter = dbCharacter.toModelCharacter()

        // THEN
        assertEquals(expectedModelCharacter, actualModelCharacter)
    }

    companion object {
        private const val CHARACTER_ID = 123
        private const val CHARACTER_NAME = "some name"
        private const val CHARACTER_STATUS = "dead"
        private const val CHARACTER_STATUS_ALIVE = false
        private const val CHARACTER_SPECIES = "some species"
        private const val CHARACTER_TYPE = "some type"
        private const val CHARACTER_GENDER = "some gender"
        private const val CHARACTER_ORIGIN_NAME = "some origin name"
        private const val CHARACTER_ORIGIN_URL = "some origin url"
        private const val CHARACTER_LOCATION_NAME = "some location name"
        private const val CHARACTER_LOCATION_URL = "some location url"
        private const val CHARACTER_IMAGE = "some image"
        private val CHARACTER_EPISODES = listOf(
            "https://test.com/api/episode/1",
            "https://test.com/api/episode/2",
            "https://test.com/api/episode/3",
        )
        private val CHARACTER_EPISODE_IDS = listOf(1, 2, 3)
        private const val CHARACTER_URL = "some url"
        private const val CHARACTER_CREATED = "some created"
        private const val CHARACTER_IS_KILLED_BY_USER = true
    }
}
