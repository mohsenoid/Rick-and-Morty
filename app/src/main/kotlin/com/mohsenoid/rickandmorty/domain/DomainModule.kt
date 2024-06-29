package com.mohsenoid.rickandmorty.domain

import com.mohsenoid.rickandmorty.domain.characters.characterModule
import com.mohsenoid.rickandmorty.domain.episodes.episodeModule

val domainModules = episodeModule + characterModule
