package com.mohsenoid.rickandmorty.ui.characters.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohsenoid.rickandmorty.domain.characters.model.Character
import com.mohsenoid.rickandmorty.domain.characters.usecase.GetCharacterDetailsUseCase
import com.mohsenoid.rickandmorty.domain.characters.usecase.UpdateCharacterStatusUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterDetailsViewModel(
    private val characterId: Int,
    private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase,
    private val updateCharacterStatusUseCase: UpdateCharacterStatusUseCase,
) : ViewModel() {
    private var character: Character? = null

    private val _uiState: MutableStateFlow<CharacterDetailsUiState> =
        MutableStateFlow(CharacterDetailsUiState.Loading)
    val uiState: StateFlow<CharacterDetailsUiState> by ::_uiState

    private val _updateStatusError: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val updateStatusError: Flow<Boolean> by ::_updateStatusError

    fun loadCharacter() {
        _uiState.value = CharacterDetailsUiState.Loading
        getCharacterDetails()
    }

    fun onKillClicked() {
        val isKilled = character?.isKilled ?: return

        viewModelScope.launch {
            when (updateCharacterStatusUseCase(characterId, !isKilled)) {
                UpdateCharacterStatusUseCase.Result.Success -> {
                    getCharacterDetails()
                }

                UpdateCharacterStatusUseCase.Result.Failure -> {
                    _updateStatusError.emit(true)
                }
            }
        }
    }

    private fun getCharacterDetails() {
        viewModelScope.launch {
            when (val result = getCharacterDetailsUseCase(characterId)) {
                is GetCharacterDetailsUseCase.Result.Success -> {
                    character = result.character
                    _uiState.value = CharacterDetailsUiState.Success(character = result.character)
                }

                GetCharacterDetailsUseCase.Result.NoInternetConnection -> {
                    _uiState.value = CharacterDetailsUiState.Error.NoConnection
                }

                is GetCharacterDetailsUseCase.Result.Failure -> {
                    _uiState.value = CharacterDetailsUiState.Error.Unknown(result.message)
                }
            }
        }
    }
}
