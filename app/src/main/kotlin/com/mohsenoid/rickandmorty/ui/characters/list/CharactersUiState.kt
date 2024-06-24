package com.mohsenoid.rickandmorty.ui.characters.list

import com.mohsenoid.rickandmorty.domain.characters.model.Character

sealed interface CharactersUiState {
    data object Loading : CharactersUiState

    data class Success(val characters: Set<Character>) : CharactersUiState

    sealed interface Error : CharactersUiState {
        data object NoConnection : Error

        data class Unknown(val message: String) : Error
    }
}
