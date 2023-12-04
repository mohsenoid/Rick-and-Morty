package com.mohsenoid.rickandmorty.character.presentation

import com.mohsenoid.rickandmorty.character.domain.model.Character

sealed interface CharacterUiState {
    data object Loading : CharacterUiState
    data class Success(val character: Character) : CharacterUiState
    data class Error(val message: String) : CharacterUiState
}
