package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.model.DbCharacter
import com.mohsenoid.rickandmorty.data.db.model.DbLocation
import com.mohsenoid.rickandmorty.data.db.model.DbOrigin
import com.mohsenoid.rickandmorty.domain.model.ModelCharacter
import com.mohsenoid.rickandmorty.domain.model.ModelLocation
import com.mohsenoid.rickandmorty.domain.model.ModelOrigin
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
    private lateinit var entityOriginMapper: Mapper<DbOrigin, ModelOrigin>

    @Mock
    private lateinit var entityLocationMapper: Mapper<DbLocation, ModelLocation>

    private lateinit var entityCharacterMapper: Mapper<DbCharacter, ModelCharacter>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        entityCharacterMapper = CharacterEntityMapper(entityOriginMapper, entityLocationMapper)
    }

    @Test
    fun map() {
        // GIVEN
        val dbCharacter: DbCharacter = CharacterDataFactory.Db.makeCharacter()

        val expectedOrigin: ModelOrigin = OriginDataFactory.Entity.makeOrigin()
        stubOriginEntityMapper(expectedOrigin)

        val expectedLocation: ModelLocation = LocationDataFactory.Entity.makeEntity()
        stubLocationEntityMapper(expectedLocation)

        val expectedCharacter = ModelCharacter(
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
            episodeIds = dbCharacter.episodeIds,
            url = dbCharacter.url,
            created = dbCharacter.created,
            killedByUser = false
        )

        // WHEN
        val actualCharacter: ModelCharacter = entityCharacterMapper.map(dbCharacter)

        // THEN
        expectedCharacter shouldEqual actualCharacter
    }

    private fun stubOriginEntityMapper(origin: ModelOrigin) {
        When calling entityOriginMapper.map(any()) itReturns origin
    }

    private fun stubLocationEntityMapper(location: ModelLocation) {
        When calling entityLocationMapper.map(any()) itReturns location
    }
}
