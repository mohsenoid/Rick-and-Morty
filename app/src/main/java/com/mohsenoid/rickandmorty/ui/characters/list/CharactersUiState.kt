package com.mohsenoid.rickandmorty.ui.characters.list

import com.mohsenoid.rickandmorty.domain.characters.model.Character

data class CharactersUiState(
    val isLoading: Boolean = false,
    val characters: Set<Character> = emptySet(),
    val isNoConnectionError: Boolean = false,
    val unknownError: String? = null,
)
