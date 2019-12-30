package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.dto.DbLocationModel
import com.mohsenoid.rickandmorty.domain.entity.LocationEntity
import javax.inject.Inject

class LocationEntityMapper @Inject internal constructor() :
    Mapper<DbLocationModel, LocationEntity> {

    override fun map(input: DbLocationModel): LocationEntity {
        return LocationEntity(
            name = input.name,
            url = input.url
        )
    }
}
