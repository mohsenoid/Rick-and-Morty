package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.dto.DbLocationModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkLocationModel
import com.mohsenoid.rickandmorty.test.LocationDataFactory
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class LocationDbMapperTest {

    lateinit var locationDbMapper: Mapper<NetworkLocationModel, DbLocationModel>

    @Before
    fun setUp() {
        locationDbMapper = LocationDbMapper()
    }

    @Test
    fun map() {
        // GIVEN
        val networkLocation = LocationDataFactory.Network.makeNetworkLocationModel()

        val expected = DbLocationModel(
            name = networkLocation.name,
            url = networkLocation.url
        )

        // WHEN
        val actual = locationDbMapper.map(networkLocation)

        // THEN
        Assert.assertEquals(expected, actual)
    }
}
