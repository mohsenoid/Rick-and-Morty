package com.mohsenoid.rickandmorty.view.character.details

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val characterDetailsFragmentModule = module {

    viewModel<CharacterDetailsContract.Presenter> {
        CharacterDetailsPresenter(
            repository = get(),
            statusProvider = get()
        )
    }
}
