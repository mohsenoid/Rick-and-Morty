package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.entity.DbEntityOrigin
import com.mohsenoid.rickandmorty.domain.entity.OriginEntity
import com.mohsenoid.rickandmorty.test.OriginDataFactory
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class OriginEntityMapperTest {

    private lateinit var entityOriginMapper: Mapper<DbEntityOrigin, OriginEntity>

    @Before
    fun setUp() {
        entityOriginMapper = OriginEntityMapper()
    }

    @Test
    fun map() {
        // GIVEN
        val dbOrigin: DbEntityOrigin = OriginDataFactory.Db.makeOrigin()

        val expectedOrigin = OriginEntity(
            name = dbOrigin.name,
            url = dbOrigin.url
        )

        // WHEN
        val actualOrigin: OriginEntity = entityOriginMapper.map(dbOrigin)

        // THEN
        expectedOrigin shouldEqual actualOrigin
    }
}
