package com.mohsenoid.rickandmorty.view.character.details

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val characterDetailsFragmentModule = module {

    viewModel { (characterId: Int) ->
        CharacterDetailsViewModel(
            characterId = characterId,
            repository = get(),
            statusProvider = get(),
        )
    }
}
