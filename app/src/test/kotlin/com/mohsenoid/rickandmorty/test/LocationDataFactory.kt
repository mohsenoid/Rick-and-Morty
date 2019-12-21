package com.mohsenoid.rickandmorty.test

import com.mohsenoid.rickandmorty.data.db.dto.DbLocationModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkLocationModel
import com.mohsenoid.rickandmorty.domain.entity.LocationEntity
import com.mohsenoid.rickandmorty.test.DataFactory.randomString

object LocationDataFactory {

    object Db {

        fun makeDbLocationModel(): DbLocationModel {
            return DbLocationModel(
                name = randomString(),
                url = randomString()
            )
        }
    }

    object Network {

        fun makeNetworkLocationModel(): NetworkLocationModel {
            return NetworkLocationModel(
                name = randomString(),
                url = randomString()
            )
        }
    }

    object Entity {

        fun makeLocationEntity(): LocationEntity {
            return LocationEntity(
                name = randomString(),
                url = randomString()
            )
        }
    }
}
