package com.mohsenoid.rickandmorty.ui.characters.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohsenoid.rickandmorty.domain.RepositoryGetResult
import com.mohsenoid.rickandmorty.domain.characters.CharactersRepository
import com.mohsenoid.rickandmorty.domain.characters.model.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CharactersViewModel(
    private val charactersRepository: CharactersRepository,
) : ViewModel() {
    private val state: MutableStateFlow<State> = MutableStateFlow(State.Loading)

    val uiState: StateFlow<CharactersUiState> =
        state.map { it.toUiState() }
            .stateIn(viewModelScope, SharingStarted.Eagerly, CharactersUiState(isLoading = true))

    fun loadCharacters(characters: Set<Int>) {
        state.value = State.Loading

        viewModelScope.launch {
            when (val result = charactersRepository.getCharacters(characters)) {
                is RepositoryGetResult.Success -> {
                    state.value = State.Success(characters = result.data)
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

        data class Success(val characters: Set<Character>) : State

        data object LoadingNoConnectionError : State

        data class LoadingUnknownError(val message: String) : State

        fun toUiState(): CharactersUiState {
            return when (this) {
                Loading -> CharactersUiState(isLoading = true)
                is Success -> CharactersUiState(characters = characters)
                is LoadingNoConnectionError -> CharactersUiState(isNoConnectionError = true)
                is LoadingUnknownError -> CharactersUiState(unknownError = message)
            }
        }
    }
}
