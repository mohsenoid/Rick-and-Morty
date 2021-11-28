package com.mohsenoid.rickandmorty.test

import com.mohsenoid.rickandmorty.data.api.model.ApiOrigin
import com.mohsenoid.rickandmorty.data.db.model.DbOrigin
import com.mohsenoid.rickandmorty.domain.model.ModelOrigin

object OriginDataFactory {

    object Db {

        fun makeOrigin(): DbOrigin {
            return DbOrigin(
                name = DataFactory.randomString(),
                url = DataFactory.randomString()
            )
        }
    }

    object Network {

        fun makeOrigin(): ApiOrigin {
            return ApiOrigin(
                name = DataFactory.randomString(),
                url = DataFactory.randomString()
            )
        }
    }

    object Entity {

        fun makeOrigin(): ModelOrigin {
            return ModelOrigin(
                name = DataFactory.randomString(),
                url = DataFactory.randomString()
            )
        }
    }
}
