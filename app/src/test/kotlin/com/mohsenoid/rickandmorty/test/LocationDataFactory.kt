package com.mohsenoid.rickandmorty.test

import com.mohsenoid.rickandmorty.data.api.model.ApiLocation
import com.mohsenoid.rickandmorty.data.db.model.DbLocation
import com.mohsenoid.rickandmorty.domain.model.ModelLocation

object LocationDataFactory {

    object Db {

        fun makeLocation(): DbLocation {
            return DbLocation(
                name = DataFactory.randomString(),
                url = DataFactory.randomString()
            )
        }
    }

    object Network {

        fun makeLocation(): ApiLocation {
            return ApiLocation(
                name = DataFactory.randomString(),
                url = DataFactory.randomString()
            )
        }
    }

    object Entity {

        fun makeEntity(): ModelLocation {
            return ModelLocation(
                name = DataFactory.randomString(),
                url = DataFactory.randomString()
            )
        }
    }
}
