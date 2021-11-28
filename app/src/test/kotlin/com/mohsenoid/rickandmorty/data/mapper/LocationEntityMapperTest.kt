package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.entity.DbEntityLocation
import com.mohsenoid.rickandmorty.domain.entity.LocationEntity
import com.mohsenoid.rickandmorty.test.LocationDataFactory
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class LocationEntityMapperTest {

    private lateinit var entityLocationMapper: Mapper<DbEntityLocation, LocationEntity>

    @Before
    fun setUp() {
        entityLocationMapper = LocationEntityMapper()
    }

    @Test
    fun map() {
        // GIVEN
        val dbLocation: DbEntityLocation = LocationDataFactory.Db.makeLocation()

        val expectedLocation = LocationEntity(
            name = dbLocation.name,
            url = dbLocation.url
        )

        // WHEN
        val actualLocation: LocationEntity = entityLocationMapper.map(dbLocation)

        // THEN
        expectedLocation shouldEqual actualLocation
    }
}
