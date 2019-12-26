package com.mohsenoid.rickandmorty.test

import com.mohsenoid.rickandmorty.data.db.dto.DbLocationModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkLocationModel
import com.mohsenoid.rickandmorty.domain.entity.LocationEntity

object LocationDataFactory {

    object Db {

        fun makeDbLocationModel(): DbLocationModel {
            return DbLocationModel(
                name = DataFactory.randomString(),
                url = DataFactory.randomString()
            )
        }
    }

    object Network {

        fun makeNetworkLocationModel(): NetworkLocationModel {
            return NetworkLocationModel(
                name = DataFactory.randomString(),
                url = DataFactory.randomString()
            )
        }
    }

    object Entity {

        fun makeLocationEntity(): LocationEntity {
            return LocationEntity(
                name = DataFactory.randomString(),
                url = DataFactory.randomString()
            )
        }
    }
}
