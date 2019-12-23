package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.dto.DbOriginModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkOriginModel
import com.mohsenoid.rickandmorty.test.OriginDataFactory
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class OriginDbMapperTest {

    lateinit var originDbMapper: Mapper<NetworkOriginModel, DbOriginModel>

    @Before
    fun setUp() {
        originDbMapper = OriginDbMapper()
    }

    @Test
    fun map() {
        // GIVEN
        val networkOrigin = OriginDataFactory.Network.makeNetworkOriginModel()

        val expected = DbOriginModel(
            name = networkOrigin.name,
            url = networkOrigin.url
        )

        // WHEN
        val actual = originDbMapper.map(networkOrigin)

        // THEN
        Assert.assertEquals(expected, actual)
    }
}
