package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.dto.DbOriginModel
import com.mohsenoid.rickandmorty.domain.entity.OriginEntity

class OriginEntityMapper : Mapper<DbOriginModel, OriginEntity> {

    override fun map(input: DbOriginModel): OriginEntity {
        return OriginEntity(
            name = input.name,
            url = input.url
        )
    }
}
