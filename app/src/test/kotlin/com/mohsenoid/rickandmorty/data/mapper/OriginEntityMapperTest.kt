package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.dto.DbOriginModel
import com.mohsenoid.rickandmorty.domain.entity.OriginEntity
import com.mohsenoid.rickandmorty.test.OriginDataFactory
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class OriginEntityMapperTest {

    private lateinit var originEntityMapper: Mapper<DbOriginModel, OriginEntity>

    @Before
    fun setUp() {
        originEntityMapper = OriginEntityMapper()
    }

    @Test
    fun map() {
        // GIVEN
        val dbOrigin: DbOriginModel = OriginDataFactory.Db.makeOrigin()

        val expectedOrigin = OriginEntity(
            name = dbOrigin.name,
            url = dbOrigin.url
        )

        // WHEN
        val actualOrigin: OriginEntity = originEntityMapper.map(dbOrigin)

        // THEN
        expectedOrigin shouldEqual actualOrigin
    }
}
