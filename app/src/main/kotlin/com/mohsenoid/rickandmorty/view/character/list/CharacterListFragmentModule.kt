package com.mohsenoid.rickandmorty.view.character.list

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val characterListFragmentModule = module {

    viewModel<CharacterListContract.Presenter> {
        CharacterListPresenter(
            repository = get(),
            statusProvider = get()
        )
    }
}
