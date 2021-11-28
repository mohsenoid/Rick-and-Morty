package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.entity.DbEntityLocation
import com.mohsenoid.rickandmorty.domain.model.ModelLocation

object LocationEntityMapper : Mapper<DbEntityLocation, ModelLocation> {

    override fun map(input: DbEntityLocation): ModelLocation {
        return ModelLocation(
            name = input.name,
            url = input.url
        )
    }
}
