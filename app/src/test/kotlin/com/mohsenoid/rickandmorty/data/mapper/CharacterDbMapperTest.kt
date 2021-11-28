package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.entity.DbEntityCharacter
import com.mohsenoid.rickandmorty.data.db.entity.DbEntityLocation
import com.mohsenoid.rickandmorty.data.db.entity.DbEntityOrigin
import com.mohsenoid.rickandmorty.data.network.dto.NetworkCharacterModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkLocationModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkOriginModel
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
    private lateinit var originDbMapperOrigin: Mapper<NetworkOriginModel, DbEntityOrigin>

    @Mock
    private lateinit var locationDbMapperLocation: Mapper<NetworkLocationModel, DbEntityLocation>

    private lateinit var characterDbMapperCharacter: Mapper<NetworkCharacterModel, DbEntityCharacter>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        characterDbMapperCharacter = CharacterDbMapper(originDbMapperOrigin, locationDbMapperLocation)
    }

    @Test
    fun map() {
        // GIVEN
        val networkCharacter: NetworkCharacterModel =
            CharacterDataFactory.Network.makeCharacter()

        val expectedOrigin: DbEntityOrigin = OriginDataFactory.Db.makeOrigin()
        stubOriginDbMapper(expectedOrigin)

        val expectedLocation: DbEntityLocation = LocationDataFactory.Db.makeLocation()
        stubLocationDbMapper(expectedLocation)

        val expectedCharacter = DbEntityCharacter(
            id = networkCharacter.id,
            name = networkCharacter.name,
            status = networkCharacter.status,
            statusAlive = networkCharacter.status.equals(
                other = CharacterDbMapper.ALIVE,
                ignoreCase = true
            ),
            species = networkCharacter.species,
            type = networkCharacter.type,
            gender = networkCharacter.gender,
            origin = expectedOrigin,
            location = expectedLocation,
            image = networkCharacter.image,
            episodeIds = CharacterDbMapper.extractEpisodeIds(networkCharacter.episodes),
            url = networkCharacter.url,
            created = networkCharacter.created,
            killedByUser = false
        )

        // WHEN
        val actualCharacter: DbEntityCharacter = characterDbMapperCharacter.map(networkCharacter)

        // THEN
        expectedCharacter shouldEqual actualCharacter
    }

    private fun stubOriginDbMapper(origin: DbEntityOrigin) {
        When calling originDbMapperOrigin.map(any()) itReturns origin
    }

    private fun stubLocationDbMapper(location: DbEntityLocation) {
        When calling locationDbMapperLocation.map(any()) itReturns location
    }
}
