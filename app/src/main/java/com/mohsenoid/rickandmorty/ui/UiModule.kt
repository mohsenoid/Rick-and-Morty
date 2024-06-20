package com.mohsenoid.rickandmorty.ui

import com.mohsenoid.rickandmorty.ui.characters.details.CharacterDetailsViewModel
import com.mohsenoid.rickandmorty.ui.characters.list.CharactersViewModel
import com.mohsenoid.rickandmorty.ui.episodes.EpisodesViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val uiModule =
    module {

        viewModelOf(::EpisodesViewModel)

        viewModelOf(::CharactersViewModel)

        viewModelOf(::CharacterDetailsViewModel)
    }
