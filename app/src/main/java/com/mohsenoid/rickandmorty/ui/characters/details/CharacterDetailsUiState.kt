package com.mohsenoid.rickandmorty.ui.characters.details

import com.mohsenoid.rickandmorty.domain.characters.model.Character

sealed interface CharacterDetailsUiState {
    object Loading : CharacterDetailsUiState

    data class Success(val character: Character) : CharacterDetailsUiState

    sealed interface Error : CharacterDetailsUiState {
        object NoConnection : Error

        data class Unknown(val message: String) : Error
    }
}
