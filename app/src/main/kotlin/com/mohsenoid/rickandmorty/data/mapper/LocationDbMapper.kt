package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.dto.DbLocationModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkLocationModel
import javax.inject.Inject

class LocationDbMapper @Inject internal constructor() :
    Mapper<NetworkLocationModel, DbLocationModel> {

    override fun map(input: NetworkLocationModel): DbLocationModel {
        return DbLocationModel(
            name = input.name,
            url = input.url
        )
    }
}
