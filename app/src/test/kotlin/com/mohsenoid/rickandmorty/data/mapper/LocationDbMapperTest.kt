package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.entity.DbEntityLocation
import com.mohsenoid.rickandmorty.data.network.dto.NetworkLocationModel
import com.mohsenoid.rickandmorty.test.LocationDataFactory
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class LocationDbMapperTest {

    private lateinit var locationDbMapperLocation: Mapper<NetworkLocationModel, DbEntityLocation>

    @Before
    fun setUp() {
        locationDbMapperLocation = LocationDbMapper()
    }

    @Test
    fun map() {
        // GIVEN
        val networkLocation: NetworkLocationModel = LocationDataFactory.Network.makeLocation()

        val expectedLocation = DbEntityLocation(
            name = networkLocation.name,
            url = networkLocation.url
        )

        // WHEN
        val actualLocation: DbEntityLocation = locationDbMapperLocation.map(networkLocation)

        // THEN
        expectedLocation shouldEqual actualLocation
    }
}
