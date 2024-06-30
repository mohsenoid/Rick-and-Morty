package com.mohsenoid.rickandmorty.ui.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohsenoid.rickandmorty.domain.episodes.model.Episode
import com.mohsenoid.rickandmorty.domain.episodes.usecase.GetEpisodesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EpisodesViewModel(
    private val getEpisodesUseCase: GetEpisodesUseCase,
) : ViewModel() {
    private var currentPage = 0
    private val episodes: MutableList<Episode> = mutableListOf()

    private val _uiState: MutableStateFlow<EpisodesUiState> =
        MutableStateFlow(EpisodesUiState.Loading)
    val uiState: StateFlow<EpisodesUiState> by ::_uiState

    fun loadEpisodes() {
        _uiState.value = EpisodesUiState.Loading
        currentPage = 0

        viewModelScope.launch {
            when (val result = getEpisodesUseCase()) {
                is GetEpisodesUseCase.Result.Success -> {
                    episodes.clear()
                    episodes += result.episodes
                    _uiState.value = EpisodesUiState.Success(episodes = episodes)
                }

                GetEpisodesUseCase.Result.EndOfList -> {
                    // Unexpected end of list!
                    _uiState.value = EpisodesUiState.Error.Unknown("End of list!")
                }

                GetEpisodesUseCase.Result.NoInternetConnection -> {
                    _uiState.value = EpisodesUiState.Error.NoConnection
                }

                is GetEpisodesUseCase.Result.Failure -> {
                    _uiState.value = EpisodesUiState.Error.Unknown(result.message)
                }
            }
        }
    }

    fun loadMoreEpisodes() {
        _uiState.value = EpisodesUiState.Success(episodes = episodes, isLoadingMore = true)
        loadMoreEpisodes(currentPage + 1)
    }

    @Suppress("LongMethod")
    private fun loadMoreEpisodes(page: Int) {
        viewModelScope.launch {
            _uiState.value =
                when (val result = getEpisodesUseCase(page)) {
                    is GetEpisodesUseCase.Result.Success -> {
                        currentPage++
                        episodes += result.episodes
                        EpisodesUiState.Success(episodes = episodes)
                    }

                    GetEpisodesUseCase.Result.EndOfList -> {
                        EpisodesUiState.Success(episodes = episodes, isEndOfList = true)
                    }

                    GetEpisodesUseCase.Result.NoInternetConnection -> {
                        EpisodesUiState.Success(episodes = episodes, isNoConnectionError = true)
                    }

                    is GetEpisodesUseCase.Result.Failure -> {
                        EpisodesUiState.Success(episodes = episodes, error = result.message)
                    }
                }
        }
    }
}
