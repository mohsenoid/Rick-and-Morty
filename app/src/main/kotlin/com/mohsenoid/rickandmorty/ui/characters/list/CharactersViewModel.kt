package com.mohsenoid.rickandmorty.ui.characters.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohsenoid.rickandmorty.domain.characters.usecase.GetCharactersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharactersViewModel(
    private val charactersIds: Set<Int>,
    private val getCharactersUseCase: GetCharactersUseCase,
) : ViewModel() {
    private val _uiState: MutableStateFlow<CharactersUiState> =
        MutableStateFlow(CharactersUiState.Loading)
    val uiState: StateFlow<CharactersUiState> by ::_uiState

    fun loadCharacters() {
        _uiState.value = CharactersUiState.Loading

        viewModelScope.launch {
            when (val result = getCharactersUseCase(charactersIds)) {
                is GetCharactersUseCase.Result.Success -> {
                    _uiState.value = CharactersUiState.Success(characters = result.characters)
                }

                GetCharactersUseCase.Result.NoInternetConnection -> {
                    _uiState.value = CharactersUiState.Error.NoConnection
                }

                is GetCharactersUseCase.Result.Failure -> {
                    _uiState.value = CharactersUiState.Error.Unknown(result.message)
                }
            }
        }
    }
}
