package com.mohsenoid.rickandmorty.test

import com.mohsenoid.rickandmorty.data.api.model.ApiOrigin
import com.mohsenoid.rickandmorty.data.db.model.DbOrigin
import com.mohsenoid.rickandmorty.domain.model.ModelOrigin

object OriginDataFactory {

    internal fun makeDbOrigin(): DbOrigin =
        DbOrigin(
            name = DataFactory.randomString(),
            url = DataFactory.randomString(),
        )

    internal fun makeApiOrigin(): ApiOrigin =
        ApiOrigin(
            name = DataFactory.randomString(),
            url = DataFactory.randomString(),
        )

    internal fun makeOrigin(): ModelOrigin =
        ModelOrigin(
            name = DataFactory.randomString(),
            url = DataFactory.randomString(),
        )
}
