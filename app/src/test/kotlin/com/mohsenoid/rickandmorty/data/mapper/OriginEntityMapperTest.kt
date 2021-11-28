package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.entity.DbEntityOrigin
import com.mohsenoid.rickandmorty.domain.model.ModelOrigin
import com.mohsenoid.rickandmorty.test.OriginDataFactory
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class OriginEntityMapperTest {

    private lateinit var entityOriginMapper: Mapper<DbEntityOrigin, ModelOrigin>

    @Before
    fun setUp() {
        entityOriginMapper = OriginEntityMapper()
    }

    @Test
    fun map() {
        // GIVEN
        val dbOrigin: DbEntityOrigin = OriginDataFactory.Db.makeOrigin()

        val expectedOrigin = ModelOrigin(
            name = dbOrigin.name,
            url = dbOrigin.url
        )

        // WHEN
        val actualOrigin: ModelOrigin = entityOriginMapper.map(dbOrigin)

        // THEN
        expectedOrigin shouldEqual actualOrigin
    }
}
