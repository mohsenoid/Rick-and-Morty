package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.dto.DbOriginModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkOriginModel
import javax.inject.Inject

class OriginDbMapper @Inject internal constructor() : Mapper<NetworkOriginModel, DbOriginModel> {

    override fun map(input: NetworkOriginModel): DbOriginModel {
        return DbOriginModel(
            name = input.name,
            url = input.url
        )
    }
}
