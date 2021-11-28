package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.entity.DbEntityOrigin
import com.mohsenoid.rickandmorty.data.network.dto.NetworkOriginModel
import com.mohsenoid.rickandmorty.test.OriginDataFactory
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class OriginDbMapperTest {

    private lateinit var originDbMapperOrigin: Mapper<NetworkOriginModel, DbEntityOrigin>

    @Before
    fun setUp() {
        originDbMapperOrigin = OriginDbMapper()
    }

    @Test
    fun map() {
        // GIVEN
        val networkOrigin: NetworkOriginModel = OriginDataFactory.Network.makeOrigin()

        val expectedOrigin = DbEntityOrigin(
            name = networkOrigin.name,
            url = networkOrigin.url
        )

        // WHEN
        val actualOrigin: DbEntityOrigin = originDbMapperOrigin.map(networkOrigin)

        // THEN
        expectedOrigin shouldEqual actualOrigin
    }
}
