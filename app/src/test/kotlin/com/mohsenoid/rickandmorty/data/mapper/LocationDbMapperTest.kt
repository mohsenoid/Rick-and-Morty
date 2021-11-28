package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.api.model.ApiLocation
import com.mohsenoid.rickandmorty.data.db.model.DbLocation
import com.mohsenoid.rickandmorty.test.LocationDataFactory
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class LocationDbMapperTest {

    private lateinit var locationDbMapperLocation: Mapper<ApiLocation, DbLocation>

    @Before
    fun setUp() {
        locationDbMapperLocation = LocationDbMapper()
    }

    @Test
    fun map() {
        // GIVEN
        val apiLocation: ApiLocation = LocationDataFactory.Network.makeLocation()

        val expectedLocation = DbLocation(
            name = apiLocation.name,
            url = apiLocation.url
        )

        // WHEN
        val actualLocation: DbLocation = locationDbMapperLocation.map(apiLocation)

        // THEN
        expectedLocation shouldEqual actualLocation
    }
}
