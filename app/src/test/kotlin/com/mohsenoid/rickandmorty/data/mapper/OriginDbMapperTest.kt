package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.api.model.ApiOrigin
import com.mohsenoid.rickandmorty.data.db.model.DbOrigin
import com.mohsenoid.rickandmorty.test.OriginDataFactory
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class OriginDbMapperTest {

    private lateinit var originDbMapperOrigin: Mapper<ApiOrigin, DbOrigin>

    @Before
    fun setUp() {
        originDbMapperOrigin = OriginDbMapper()
    }

    @Test
    fun map() {
        // GIVEN
        val apiOrigin: ApiOrigin = OriginDataFactory.Network.makeOrigin()

        val expectedOrigin = DbOrigin(
            name = apiOrigin.name,
            url = apiOrigin.url
        )

        // WHEN
        val actualOrigin: DbOrigin = originDbMapperOrigin.map(apiOrigin)

        // THEN
        expectedOrigin shouldEqual actualOrigin
    }
}
