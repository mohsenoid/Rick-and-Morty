package com.mohsenoid.rickandmorty

import com.mohsenoid.rickandmorty.data.dataModules
import com.mohsenoid.rickandmorty.domain.domainModules
import com.mohsenoid.rickandmorty.ui.uiModule

val appModules = dataModules + domainModules + uiModule
