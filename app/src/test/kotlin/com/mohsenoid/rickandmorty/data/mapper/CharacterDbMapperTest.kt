package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.Serializer
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
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
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
        val networkCharacter = CharacterDataFactory.Network.makeNetworkCharacterModel()

        val expectedOrigin = OriginDataFactory.Db.makeDbOriginModel()
        stubOriginDbMapper(expectedOrigin)

        val expectedLocation = LocationDataFactory.Db.makeDbLocationModel()
        stubLocationDbMapper(expectedLocation)

        val expected = DbCharacterModel(
            id = networkCharacter.id,
            name = networkCharacter.name,
            status = networkCharacter.status,
            statusAlive = networkCharacter.status.equals(CharacterDbMapper.ALIVE, true),
            species = networkCharacter.species,
            type = networkCharacter.type,
            gender = networkCharacter.gender,
            origin = expectedOrigin,
            location = expectedLocation,
            image = networkCharacter.image,
            serializedEpisodes = Serializer.serializeStringList(networkCharacter.episodes),
            url = networkCharacter.url,
            created = networkCharacter.created,
            killedByUser = false
        )

        // WHEN
        val actual = characterDbMapper.map(networkCharacter)

        // THEN
        assertEquals(expected, actual)
    }

    private fun stubOriginDbMapper(origin: DbOriginModel) {
        whenever(originDbMapper.map(any()))
            .thenReturn(origin)
    }

    private fun stubLocationDbMapper(location: DbLocationModel) {
        whenever(locationDbMapper.map(any()))
            .thenReturn(location)
    }
}
