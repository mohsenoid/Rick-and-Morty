package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.entity.DbEntityCharacter
import com.mohsenoid.rickandmorty.data.db.entity.DbEntityLocation
import com.mohsenoid.rickandmorty.data.db.entity.DbEntityOrigin
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
    private lateinit var entityOriginMapper: Mapper<DbEntityOrigin, OriginEntity>

    @Mock
    private lateinit var entityLocationMapper: Mapper<DbEntityLocation, LocationEntity>

    private lateinit var entityCharacterMapper: Mapper<DbEntityCharacter, CharacterEntity>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        entityCharacterMapper = CharacterEntityMapper(entityOriginMapper, entityLocationMapper)
    }

    @Test
    fun map() {
        // GIVEN
        val dbCharacter: DbEntityCharacter = CharacterDataFactory.Db.makeCharacter()

        val expectedOrigin: OriginEntity = OriginDataFactory.Entity.makeOrigin()
        stubOriginEntityMapper(expectedOrigin)

        val expectedLocation: LocationEntity = LocationDataFactory.Entity.makeEntity()
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
            episodeIds = dbCharacter.episodeIds,
            url = dbCharacter.url,
            created = dbCharacter.created,
            killedByUser = false
        )

        // WHEN
        val actualCharacter: CharacterEntity = entityCharacterMapper.map(dbCharacter)

        // THEN
        expectedCharacter shouldEqual actualCharacter
    }

    private fun stubOriginEntityMapper(origin: OriginEntity) {
        When calling entityOriginMapper.map(any()) itReturns origin
    }

    private fun stubLocationEntityMapper(location: LocationEntity) {
        When calling entityLocationMapper.map(any()) itReturns location
    }
}
