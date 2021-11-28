package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.entity.DbEntityLocation
import com.mohsenoid.rickandmorty.domain.entity.LocationEntity

class LocationEntityMapper : Mapper<DbEntityLocation, LocationEntity> {

    override fun map(input: DbEntityLocation): LocationEntity {
        return LocationEntity(
            name = input.name,
            url = input.url
        )
    }
}
