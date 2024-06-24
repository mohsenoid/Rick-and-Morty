package com.mohsenoid.rickandmorty.ui.characters.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohsenoid.rickandmorty.domain.RepositoryGetResult
import com.mohsenoid.rickandmorty.domain.characters.CharacterRepository
import com.mohsenoid.rickandmorty.domain.characters.model.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterDetailsViewModel(
    private val characterId: Int,
    private val characterRepository: CharacterRepository,
) : ViewModel() {
    private var character: Character? = null

    private val _uiState: MutableStateFlow<CharacterDetailsUiState> =
        MutableStateFlow(CharacterDetailsUiState.Loading)
    val uiState: StateFlow<CharacterDetailsUiState> by ::_uiState

    fun loadCharacter() {
        _uiState.value = CharacterDetailsUiState.Loading

        viewModelScope.launch {
            val result = characterRepository.getCharacter(characterId)
            updateUiState(result)
        }
    }

    fun onKillClicked() {
        val character = character ?: return

        viewModelScope.launch {
            val result =
                characterRepository.updateCharacterStatus(characterId, !character.isKilled)
            updateUiState(result)
        }
    }

    private fun updateUiState(result: RepositoryGetResult<Character>) {
        when (result) {
            is RepositoryGetResult.Success -> {
                character = result.data
                _uiState.value = CharacterDetailsUiState.Success(character = result.data)
            }

            is RepositoryGetResult.Failure.EndOfList -> {
                _uiState.value = CharacterDetailsUiState.Error.Unknown(result.message)
            }

            is RepositoryGetResult.Failure.NoConnection -> {
                _uiState.value = CharacterDetailsUiState.Error.NoConnection
            }

            is RepositoryGetResult.Failure.Unknown -> {
                _uiState.value = CharacterDetailsUiState.Error.Unknown(result.message)
            }
        }
    }
}
