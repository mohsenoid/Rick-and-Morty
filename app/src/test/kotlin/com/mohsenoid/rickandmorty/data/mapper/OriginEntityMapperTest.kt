package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.dto.DbOriginModel
import com.mohsenoid.rickandmorty.domain.entity.OriginEntity
import com.mohsenoid.rickandmorty.test.OriginDataFactory
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class OriginEntityMapperTest {

    lateinit var originEntityMapper: Mapper<DbOriginModel, OriginEntity>

    @Before
    fun setUp() {
        originEntityMapper = OriginEntityMapper()
    }

    @Test
    fun map() {
        // GIVEN
        val dbOrigin = OriginDataFactory.Db.makeDbOriginModel()

        val expected = OriginEntity(
            name = dbOrigin.name,
            url = dbOrigin.url
        )

        // WHEN
        val actual = originEntityMapper.map(dbOrigin)

        // THEN
        Assert.assertEquals(expected, actual)
    }
}
