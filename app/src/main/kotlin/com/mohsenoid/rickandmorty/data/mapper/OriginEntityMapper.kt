package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.entity.DbEntityOrigin
import com.mohsenoid.rickandmorty.domain.model.ModelOrigin

object OriginEntityMapper : Mapper<DbEntityOrigin, ModelOrigin> {

    override fun map(input: DbEntityOrigin): ModelOrigin {
        return ModelOrigin(
            name = input.name,
            url = input.url
        )
    }
}
