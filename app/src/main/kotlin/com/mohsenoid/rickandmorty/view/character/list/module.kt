package com.mohsenoid.rickandmorty.view.character.list

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val characterListFragmentModule = module {

    viewModel { (characterIds: List<Int>) ->
        CharacterListViewModel(
            characterIds = characterIds,
            repository = get(),
            statusProvider = get()
        )
    }
}
