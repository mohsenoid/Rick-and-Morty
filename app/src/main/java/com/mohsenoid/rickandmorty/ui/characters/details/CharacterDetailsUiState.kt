package com.mohsenoid.rickandmorty.ui.characters.details

import com.mohsenoid.rickandmorty.domain.characters.model.Character

data class CharacterDetailsUiState(
    val isLoading: Boolean = false,
    val character: Character? = null,
    val isNoConnectionError: Boolean = false,
    val unknownError: String? = null,
)
