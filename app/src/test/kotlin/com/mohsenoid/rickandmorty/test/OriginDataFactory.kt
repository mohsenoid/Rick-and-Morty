package com.mohsenoid.rickandmorty.test

import com.mohsenoid.rickandmorty.data.db.dto.DbOriginModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkOriginModel
import com.mohsenoid.rickandmorty.domain.entity.OriginEntity
import com.mohsenoid.rickandmorty.test.DataFactory.randomString

object OriginDataFactory {

    object Db {

        fun makeDbOriginModel(): DbOriginModel {
            return DbOriginModel(
                name = randomString(),
                url = randomString()
            )
        }
    }

    object Network {

        fun makeNetworkOriginModel(): NetworkOriginModel {
            return NetworkOriginModel(
                name = randomString(),
                url = randomString()
            )
        }
    }

    object Entity {

        fun makeOriginEntity(): OriginEntity {
            return OriginEntity(
                name = randomString(),
                url = randomString()
            )
        }
    }
}
