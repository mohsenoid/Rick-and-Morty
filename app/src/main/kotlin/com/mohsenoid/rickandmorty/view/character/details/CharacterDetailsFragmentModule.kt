package com.mohsenoid.rickandmorty.view.character.details

import org.koin.core.qualifier.named
import org.koin.dsl.module

val characterDetailsFragmentModule = module {

    scope(named<CharacterDetailsFragment>()) {

        scoped<CharacterDetailsContract.Presenter> {
            CharacterDetailsPresenter(repository = get(), configProvider = get())
        }
    }
}
