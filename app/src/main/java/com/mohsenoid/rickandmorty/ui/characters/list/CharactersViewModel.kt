package com.mohsenoid.rickandmorty.ui.characters.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohsenoid.rickandmorty.domain.RepositoryGetResult
import com.mohsenoid.rickandmorty.domain.characters.CharactersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharactersViewModel(
    private val charactersIds: Set<Int>,
    private val charactersRepository: CharactersRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<CharactersUiState> =
        MutableStateFlow(CharactersUiState.Loading)
    val uiState: StateFlow<CharactersUiState> by ::_uiState

    fun loadCharacters() {
        _uiState.value = CharactersUiState.Loading

        viewModelScope.launch {
            when (val result = charactersRepository.getCharacters(charactersIds)) {
                is RepositoryGetResult.Success -> {
                    _uiState.value = CharactersUiState.Success(characters = result.data)
                }

                is RepositoryGetResult.Failure.EndOfList -> {
                    _uiState.value = CharactersUiState.Error.Unknown(result.message)
                }

                is RepositoryGetResult.Failure.NoConnection -> {
                    _uiState.value = CharactersUiState.Error.NoConnection
                }

                is RepositoryGetResult.Failure.Unknown -> {
                    _uiState.value = CharactersUiState.Error.Unknown(result.message)
                }
            }
        }
    }
}
