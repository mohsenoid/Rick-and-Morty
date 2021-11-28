package com.mohsenoid.rickandmorty.test

import com.mohsenoid.rickandmorty.data.api.model.ApiLocation
import com.mohsenoid.rickandmorty.data.db.model.DbLocation
import com.mohsenoid.rickandmorty.domain.model.ModelLocation

object LocationDataFactory {

    internal fun makeDbLocation(): DbLocation =
        DbLocation(
            name = DataFactory.randomString(),
            url = DataFactory.randomString(),
        )

    internal fun makeApiLocation(): ApiLocation =
        ApiLocation(
            name = DataFactory.randomString(),
            url = DataFactory.randomString(),
        )

    internal fun makeLocation(): ModelLocation =
        ModelLocation(
            name = DataFactory.randomString(),
            url = DataFactory.randomString(),
        )
}
