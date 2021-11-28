package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.entity.DbEntityLocation
import com.mohsenoid.rickandmorty.domain.model.ModelLocation
import com.mohsenoid.rickandmorty.test.LocationDataFactory
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class LocationEntityMapperTest {

    private lateinit var entityLocationMapper: Mapper<DbEntityLocation, ModelLocation>

    @Before
    fun setUp() {
        entityLocationMapper = LocationEntityMapper()
    }

    @Test
    fun map() {
        // GIVEN
        val dbLocation: DbEntityLocation = LocationDataFactory.Db.makeLocation()

        val expectedLocation = ModelLocation(
            name = dbLocation.name,
            url = dbLocation.url
        )

        // WHEN
        val actualLocation: ModelLocation = entityLocationMapper.map(dbLocation)

        // THEN
        expectedLocation shouldEqual actualLocation
    }
}
