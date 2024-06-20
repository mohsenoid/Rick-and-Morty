package com.mohsenoid.rickandmorty.ui.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohsenoid.rickandmorty.domain.RepositoryGetResult
import com.mohsenoid.rickandmorty.domain.episodes.EpisodesRepository
import com.mohsenoid.rickandmorty.domain.episodes.model.Episode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EpisodesViewModel(
    private val episodesRepository: EpisodesRepository,
) : ViewModel() {
    private val state: MutableStateFlow<State> = MutableStateFlow(State.Loading)

    val uiState: StateFlow<EpisodesUiState> =
        state.map { it.toUiState() }
            .stateIn(viewModelScope, SharingStarted.Eagerly, EpisodesUiState())

    fun loadEpisodes() {
        state.value = State.Loading

        viewModelScope.launch {
            when (val result = episodesRepository.getEpisodes()) {
                is RepositoryGetResult.Success -> {
                    state.value = State.Success(page = 0, episodes = result.data)
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

    fun onEndOfListReached() {
        state.update { currentState ->
            when (currentState) {
                State.Loading,
                is State.LoadingNoConnectionError,
                is State.LoadingUnknownError,
                is State.LoadingMore,
                -> {
                    // no op
                    return
                }

                is State.Success -> {
                    loadMoreEpisodes(currentState.page + 1)
                    currentState.toLoadingMore()
                }

                is State.LoadingMoreNoConnectionError -> {
                    loadMoreEpisodes(currentState.page + 1)
                    currentState.toLoadingMore()
                }

                is State.LoadingMoreUnknownError -> {
                    loadMoreEpisodes(currentState.page + 1)
                    currentState.toLoadingMore()
                }
            }
        }
    }

    @Suppress("LongMethod")
    private fun loadMoreEpisodes(page: Int) {
        viewModelScope.launch {
            when (val result = episodesRepository.getEpisodes(page)) {
                is RepositoryGetResult.Success -> {
                    state.update { currentState ->
                        when (currentState) {
                            State.Loading,
                            is State.LoadingUnknownError,
                            is State.LoadingNoConnectionError,
                            is State.LoadingMoreUnknownError,
                            is State.LoadingMoreNoConnectionError,
                            is State.Success,
                            -> {
                                // no op
                                return@launch
                            }

                            is State.LoadingMore -> {
                                State.Success(
                                    page = page + 1,
                                    episodes = currentState.episodes + result.data,
                                )
                            }
                        }
                    }
                }

                is RepositoryGetResult.Failure.EndOfList -> {
                    state.update { currentState ->
                        when (currentState) {
                            State.Loading,
                            is State.LoadingNoConnectionError,
                            is State.LoadingUnknownError,
                            is State.LoadingMoreNoConnectionError,
                            is State.LoadingMoreUnknownError,
                            is State.Success,
                            -> {
                                // no op
                                return@launch
                            }

                            is State.LoadingMore -> {
                                currentState.toSuccess(isEndOfList = true)
                            }
                        }
                    }
                }

                is RepositoryGetResult.Failure.NoConnection -> {
                    state.update { currentState ->
                        when (currentState) {
                            State.Loading,
                            is State.LoadingNoConnectionError,
                            is State.LoadingUnknownError,
                            is State.LoadingMoreNoConnectionError,
                            is State.LoadingMoreUnknownError,
                            is State.Success,
                            -> {
                                // no op
                                return@launch
                            }

                            is State.LoadingMore -> {
                                currentState.toLoadingMoreNoConnectionError()
                            }
                        }
                    }
                }

                is RepositoryGetResult.Failure.Unknown -> {
                    state.update { currentState ->
                        when (currentState) {
                            State.Loading,
                            is State.LoadingNoConnectionError,
                            is State.LoadingUnknownError,
                            is State.LoadingMoreNoConnectionError,
                            is State.LoadingMoreUnknownError,
                            is State.Success,
                            -> {
                                // no op
                                return@launch
                            }

                            is State.LoadingMore -> {
                                currentState.toLoadingMoreUnknownError(result.message)
                            }
                        }
                    }
                }
            }
        }
    }

    internal sealed interface State {
        data object Loading : State

        data class Success(
            val page: Int,
            val episodes: List<Episode>,
            val isEndOfList: Boolean = false,
        ) : State {
            fun toLoadingMore() = LoadingMore(page, episodes)
        }

        data object LoadingNoConnectionError : State

        data class LoadingUnknownError(val message: String) : State

        data class LoadingMore(val page: Int, val episodes: List<Episode>) : State {
            fun toSuccess(isEndOfList: Boolean) = Success(page, episodes, isEndOfList)

            fun toLoadingMoreNoConnectionError() = LoadingMoreNoConnectionError(page, episodes)

            fun toLoadingMoreUnknownError(message: String) = LoadingMoreUnknownError(page, episodes, message)
        }

        data class LoadingMoreNoConnectionError(
            val page: Int,
            val episodes: List<Episode>,
        ) : State {
            fun toLoadingMore() = LoadingMore(page, episodes)
        }

        data class LoadingMoreUnknownError(
            val page: Int,
            val episodes: List<Episode>,
            val message: String,
        ) : State {
            fun toLoadingMore() = LoadingMore(page, episodes)
        }

        fun toUiState(): EpisodesUiState {
            return when (this) {
                Loading -> {
                    EpisodesUiState(isLoading = true)
                }

                is Success -> {
                    EpisodesUiState(episodes = episodes, isEndOfList = isEndOfList)
                }

                is LoadingNoConnectionError -> {
                    EpisodesUiState(isNoConnectionError = true)
                }

                is LoadingUnknownError -> {
                    EpisodesUiState(unknownError = message)
                }

                is LoadingMore -> {
                    EpisodesUiState(episodes = episodes, isLoadingMore = true)
                }

                is LoadingMoreNoConnectionError -> {
                    EpisodesUiState(episodes = episodes, isLoadMoreNoConnectionError = true)
                }

                is LoadingMoreUnknownError -> {
                    EpisodesUiState(episodes = episodes, moreError = message)
                }
            }
        }
    }
}
