package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.dto.DbLocationModel
import com.mohsenoid.rickandmorty.domain.entity.LocationEntity

class LocationEntityMapper : Mapper<DbLocationModel, LocationEntity> {

    override fun map(input: DbLocationModel): LocationEntity {
        return LocationEntity(
            name = input.name,
            url = input.url
        )
    }
}
