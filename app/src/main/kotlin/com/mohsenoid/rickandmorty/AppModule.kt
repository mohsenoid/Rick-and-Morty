package com.mohsenoid.rickandmorty

import com.mohsenoid.rickandmorty.data.dataModules
import com.mohsenoid.rickandmorty.domain.domainModules
import com.mohsenoid.rickandmorty.ui.uiModule
import org.koin.core.qualifier.named
import org.koin.dsl.module

@Suppress("TopLevelPropertyNaming")
const val BASE_URL_QUALIFIER = "BASE_URL"

private val appModule =
    module {
        single(named(BASE_URL_QUALIFIER)) {
            BuildConfig.API_BASE_URL
        }
    }

val appModules = appModule + uiModule + domainModules + dataModules
