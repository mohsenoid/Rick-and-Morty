package com.mohsenoid.rickandmorty.view.character.list

import org.koin.core.qualifier.named
import org.koin.dsl.module

val characterListFragmentModule = module {

    scope(named<CharacterListFragment>()) {

        scoped<CharacterListContract.Presenter> {
            CharacterListPresenter(
                repository = get(),
                configProvider = get()
            )
        }
    }
}
