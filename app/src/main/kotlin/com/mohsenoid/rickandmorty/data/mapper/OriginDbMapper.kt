package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.entity.DbEntityOrigin
import com.mohsenoid.rickandmorty.data.network.dto.NetworkOriginModel

class OriginDbMapper : Mapper<NetworkOriginModel, DbEntityOrigin> {

    override fun map(input: NetworkOriginModel): DbEntityOrigin {
        return DbEntityOrigin(
            name = input.name,
            url = input.url
        )
    }
}
