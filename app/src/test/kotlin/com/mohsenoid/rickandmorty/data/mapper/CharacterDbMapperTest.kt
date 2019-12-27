package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.dto.DbCharacterModel
import com.mohsenoid.rickandmorty.data.db.dto.DbLocationModel
import com.mohsenoid.rickandmorty.data.db.dto.DbOriginModel
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
    private lateinit var originDbMapper: Mapper<NetworkOriginModel, DbOriginModel>

    @Mock
    private lateinit var locationDbMapper: Mapper<NetworkLocationModel, DbLocationModel>

    private lateinit var characterDbMapper: Mapper<NetworkCharacterModel, DbCharacterModel>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        characterDbMapper = CharacterDbMapper(originDbMapper, locationDbMapper)
    }

    @Test
    fun map() {
        // GIVEN
        val networkCharacter: NetworkCharacterModel =
            CharacterDataFactory.Network.makeCharacter()

        val expectedOrigin: DbOriginModel = OriginDataFactory.Db.makeOrigin()
        stubOriginDbMapper(expectedOrigin)

        val expectedLocation: DbLocationModel = LocationDataFactory.Db.makeLocation()
        stubLocationDbMapper(expectedLocation)

        val expectedCharacter = DbCharacterModel(
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
        val actualCharacter: DbCharacterModel = characterDbMapper.map(networkCharacter)

        // THEN
        expectedCharacter shouldEqual actualCharacter
    }

    private fun stubOriginDbMapper(origin: DbOriginModel) {
        When calling originDbMapper.map(any()) itReturns origin
    }

    private fun stubLocationDbMapper(location: DbLocationModel) {
        When calling locationDbMapper.map(any()) itReturns location
    }
}
