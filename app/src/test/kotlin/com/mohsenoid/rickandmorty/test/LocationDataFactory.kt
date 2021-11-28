package com.mohsenoid.rickandmorty.test

import com.mohsenoid.rickandmorty.data.db.entity.DbEntityLocation
import com.mohsenoid.rickandmorty.data.network.dto.NetworkLocationModel
import com.mohsenoid.rickandmorty.domain.entity.LocationEntity

object LocationDataFactory {

    object Db {

        fun makeLocation(): DbEntityLocation {
            return DbEntityLocation(
                name = DataFactory.randomString(),
                url = DataFactory.randomString()
            )
        }
    }

    object Network {

        fun makeLocation(): NetworkLocationModel {
            return NetworkLocationModel(
                name = DataFactory.randomString(),
                url = DataFactory.randomString()
            )
        }
    }

    object Entity {

        fun makeEntity(): LocationEntity {
            return LocationEntity(
                name = DataFactory.randomString(),
                url = DataFactory.randomString()
            )
        }
    }
}
