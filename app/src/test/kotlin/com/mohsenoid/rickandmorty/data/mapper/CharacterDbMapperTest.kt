package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.api.model.ApiCharacter
import com.mohsenoid.rickandmorty.data.api.model.ApiLocation
import com.mohsenoid.rickandmorty.data.api.model.ApiOrigin
import com.mohsenoid.rickandmorty.data.db.model.DbCharacter
import com.mohsenoid.rickandmorty.data.db.model.DbLocation
import com.mohsenoid.rickandmorty.data.db.model.DbOrigin
import com.mohsenoid.rickandmorty.test.CharacterDataFactory
import com.mohsenoid.rickandmorty.test.LocationDataFactory
import com.mohsenoid.rickandmorty.test.OriginDataFactory
import com.nhaarman.mockitokotlin2.any
import org.amshove.kluent.When
import org.amshove.kluent.calling
import org.amshove.kluent.itReturns
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class CharacterDbMapperTest {

    @Mock
    private lateinit var originDbMapperOrigin: Mapper<ApiOrigin, DbOrigin>

    @Mock
    private lateinit var locationDbMapperLocation: Mapper<ApiLocation, DbLocation>

    private lateinit var characterDbMapperCharacter: Mapper<ApiCharacter, DbCharacter>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        characterDbMapperCharacter = CharacterDbMapper(originDbMapperOrigin, locationDbMapperLocation)
    }

    @Test
    fun map() {
        // GIVEN
        val apiCharacter: ApiCharacter =
            CharacterDataFactory.Network.makeCharacter()

        val expectedOrigin: DbOrigin = OriginDataFactory.Db.makeOrigin()
        stubOriginDbMapper(expectedOrigin)

        val expectedLocation: DbLocation = LocationDataFactory.Db.makeLocation()
        stubLocationDbMapper(expectedLocation)

        val expectedCharacter = DbCharacter(
            id = apiCharacter.id,
            name = apiCharacter.name,
            status = apiCharacter.status,
            statusAlive = apiCharacter.status.equals(
                other = CharacterDbMapper.ALIVE,
                ignoreCase = true
            ),
            species = apiCharacter.species,
            type = apiCharacter.type,
            gender = apiCharacter.gender,
            origin = expectedOrigin,
            location = expectedLocation,
            image = apiCharacter.image,
            episodeIds = CharacterDbMapper.extractEpisodeIds(apiCharacter.episodes),
            url = apiCharacter.url,
            created = apiCharacter.created,
            killedByUser = false
        )

        // WHEN
        val actualCharacter: DbCharacter = characterDbMapperCharacter.map(apiCharacter)

        // THEN
        expectedCharacter shouldEqual actualCharacter
    }

    private fun stubOriginDbMapper(origin: DbOrigin) {
        When calling originDbMapperOrigin.map(any()) itReturns origin
    }

    private fun stubLocationDbMapper(location: DbLocation) {
        When calling locationDbMapperLocation.map(any()) itReturns location
    }
}
