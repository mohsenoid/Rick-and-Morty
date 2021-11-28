package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.entity.DbEntityLocation
import com.mohsenoid.rickandmorty.data.network.dto.NetworkLocationModel

class LocationDbMapper : Mapper<NetworkLocationModel, DbEntityLocation> {

    override fun map(input: NetworkLocationModel): DbEntityLocation {
        return DbEntityLocation(
            name = input.name,
            url = input.url
        )
    }
}
