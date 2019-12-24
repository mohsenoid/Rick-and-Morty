package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.Serializer
import com.mohsenoid.rickandmorty.data.db.dto.DbCharacterModel
import com.mohsenoid.rickandmorty.data.db.dto.DbLocationModel
import com.mohsenoid.rickandmorty.data.db.dto.DbOriginModel
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity
import com.mohsenoid.rickandmorty.domain.entity.LocationEntity
import com.mohsenoid.rickandmorty.domain.entity.OriginEntity
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

class CharacterEntityMapperTest {

    @Mock
    private lateinit var originEntityMapper: Mapper<DbOriginModel, OriginEntity>

    @Mock
    private lateinit var locationEntityMapper: Mapper<DbLocationModel, LocationEntity>

    lateinit var characterEntityMapper: Mapper<DbCharacterModel, CharacterEntity>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        characterEntityMapper = CharacterEntityMapper(originEntityMapper, locationEntityMapper)
    }

    @Test
    fun map() {
        // GIVEN
        val dbCharacter = CharacterDataFactory.Db.makeDbCharacterModel()

        val expectedOrigin = OriginDataFactory.Entity.makeOriginEntity()
        stubOriginEntityMapper(expectedOrigin)

        val expectedLocation = LocationDataFactory.Entity.makeLocationEntity()
        stubLocationEntityMapper(expectedLocation)

        val expectedCharacter = CharacterEntity(
            id = dbCharacter.id,
            name = dbCharacter.name,
            status = dbCharacter.status,
            statusAlive = dbCharacter.statusAlive,
            species = dbCharacter.species,
            type = dbCharacter.type,
            gender = dbCharacter.gender,
            origin = expectedOrigin,
            location = expectedLocation,
            imageUrl = dbCharacter.image,
            episodeIds = Serializer.mapStringListToIntegerList(
                CharacterEntityMapper.getEpisodeIds(
                    Serializer.deserializeStringList(dbCharacter.serializedEpisodes)
                )
            ),
            url = dbCharacter.url,
            created = dbCharacter.created,
            killedByUser = false
        )

        // WHEN
        val actualCharacter = characterEntityMapper.map(dbCharacter)

        // THEN
        expectedCharacter shouldEqual actualCharacter
    }

    private fun stubOriginEntityMapper(origin: OriginEntity) {
        When calling originEntityMapper.map(any()) itReturns origin
    }

    private fun stubLocationEntityMapper(location: LocationEntity) {
        When calling locationEntityMapper.map(any()) itReturns location
    }
}
