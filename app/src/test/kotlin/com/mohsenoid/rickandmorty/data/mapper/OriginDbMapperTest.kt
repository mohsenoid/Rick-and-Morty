package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.dto.DbOriginModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkOriginModel
import com.mohsenoid.rickandmorty.test.OriginDataFactory
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class OriginDbMapperTest {

    private lateinit var originDbMapper: Mapper<NetworkOriginModel, DbOriginModel>

    @Before
    fun setUp() {
        originDbMapper = OriginDbMapper()
    }

    @Test
    fun map() {
        // GIVEN
        val networkOrigin: NetworkOriginModel = OriginDataFactory.Network.makeOrigin()

        val expectedOrigin = DbOriginModel(
            name = networkOrigin.name,
            url = networkOrigin.url
        )

        // WHEN
        val actualOrigin: DbOriginModel = originDbMapper.map(networkOrigin)

        // THEN
        expectedOrigin shouldEqual actualOrigin
    }
}
