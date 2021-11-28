package com.mohsenoid.rickandmorty.test

import com.mohsenoid.rickandmorty.data.db.entity.DbEntityOrigin
import com.mohsenoid.rickandmorty.data.network.dto.NetworkOriginModel
import com.mohsenoid.rickandmorty.domain.entity.OriginEntity

object OriginDataFactory {

    object Db {

        fun makeOrigin(): DbEntityOrigin {
            return DbEntityOrigin(
                name = DataFactory.randomString(),
                url = DataFactory.randomString()
            )
        }
    }

    object Network {

        fun makeOrigin(): NetworkOriginModel {
            return NetworkOriginModel(
                name = DataFactory.randomString(),
                url = DataFactory.randomString()
            )
        }
    }

    object Entity {

        fun makeOrigin(): OriginEntity {
            return OriginEntity(
                name = DataFactory.randomString(),
                url = DataFactory.randomString()
            )
        }
    }
}
