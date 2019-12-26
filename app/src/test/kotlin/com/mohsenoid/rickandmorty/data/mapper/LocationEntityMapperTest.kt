package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.dto.DbLocationModel
import com.mohsenoid.rickandmorty.domain.entity.LocationEntity
import com.mohsenoid.rickandmorty.test.LocationDataFactory
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class LocationEntityMapperTest {

    private lateinit var locationEntityMapper: Mapper<DbLocationModel, LocationEntity>

    @Before
    fun setUp() {
        locationEntityMapper = LocationEntityMapper()
    }

    @Test
    fun map() {
        // GIVEN
        val dbLocation: DbLocationModel = LocationDataFactory.Db.makeLocation()

        val expectedLocation = LocationEntity(
            name = dbLocation.name,
            url = dbLocation.url
        )

        // WHEN
        val actualLocation: LocationEntity = locationEntityMapper.map(dbLocation)

        // THEN
        expectedLocation shouldEqual actualLocation
    }
}
