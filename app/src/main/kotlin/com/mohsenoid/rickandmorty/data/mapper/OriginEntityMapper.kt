package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.entity.DbEntityOrigin
import com.mohsenoid.rickandmorty.domain.entity.OriginEntity

class OriginEntityMapper : Mapper<DbEntityOrigin, OriginEntity> {

    override fun map(input: DbEntityOrigin): OriginEntity {
        return OriginEntity(
            name = input.name,
            url = input.url
        )
    }
}
