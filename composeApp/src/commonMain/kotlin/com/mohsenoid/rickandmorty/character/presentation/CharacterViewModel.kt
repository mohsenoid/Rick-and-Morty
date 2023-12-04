package com.mohsenoid.rickandmorty.character.presentation

import com.mohsenoid.rickandmorty.character.domain.CharacterRepository
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val characterId: Int,
    private val repository: CharacterRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<CharacterUiState> = MutableStateFlow(CharacterUiState.Loading)
    val uiState: StateFlow<CharacterUiState> by ::_uiState

    fun updateCharacter() {
        viewModelScope.launch {
            val character = repository.getCharacter(characterId)
            _uiState.value = CharacterUiState.Success(character)
        }
    }

    override fun onCleared() {
        super.onCleared()
        repository.close()
    }
}
