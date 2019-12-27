package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.dto.DbLocationModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkLocationModel
import com.mohsenoid.rickandmorty.test.LocationDataFactory
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class LocationDbMapperTest {

    private lateinit var locationDbMapper: Mapper<NetworkLocationModel, DbLocationModel>

    @Before
    fun setUp() {
        locationDbMapper = LocationDbMapper()
    }

    @Test
    fun map() {
        // GIVEN
        val networkLocation: NetworkLocationModel = LocationDataFactory.Network.makeLocation()

        val expectedLocation = DbLocationModel(
            name = networkLocation.name,
            url = networkLocation.url
        )

        // WHEN
        val actualLocation: DbLocationModel = locationDbMapper.map(networkLocation)

        // THEN
        expectedLocation shouldEqual actualLocation
    }
}
