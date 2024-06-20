package com.mohsenoid.rickandmorty.ui.characters.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohsenoid.rickandmorty.domain.RepositoryGetResult
import com.mohsenoid.rickandmorty.domain.characters.CharactersRepository
import com.mohsenoid.rickandmorty.domain.characters.model.Character
import com.mohsenoid.rickandmorty.ui.characters.list.CharactersViewModel.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CharacterDetailsViewModel(
    private val charactersRepository: CharactersRepository,
) : ViewModel() {
    private val state: MutableStateFlow<State> = MutableStateFlow(State.Loading)

    val uiState: StateFlow<CharacterDetailsUiState> =
        state.map { it.toUiState() }
            .stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                CharacterDetailsUiState(isLoading = true),
            )

    fun loadCharacter(characterId: Int) {
        state.value = State.Loading

        viewModelScope.launch {
            when (val result = charactersRepository.getCharacter(characterId)) {
                is RepositoryGetResult.Success -> {
                    state.value = State.Success(character = result.data)
                }

                is RepositoryGetResult.Failure.EndOfList -> {
                    state.value = State.LoadingUnknownError(result.message)
                }

                is RepositoryGetResult.Failure.NoConnection -> {
                    state.value = State.LoadingNoConnectionError
                }

                is RepositoryGetResult.Failure.Unknown -> {
                    state.value = State.LoadingUnknownError(result.message)
                }
            }
        }
    }

    internal sealed interface State {
        data object Loading : State

        data class Success(val character: Character) : State

        data object LoadingNoConnectionError : State

        data class LoadingUnknownError(val message: String) : State

        fun toUiState(): CharacterDetailsUiState {
            return when (this) {
                Loading -> CharacterDetailsUiState(isLoading = true)
                is Success -> CharacterDetailsUiState(character = character)
                is LoadingNoConnectionError -> CharacterDetailsUiState(isNoConnectionError = true)
                is LoadingUnknownError -> CharacterDetailsUiState(unknownError = message)
            }
        }
    }
}
