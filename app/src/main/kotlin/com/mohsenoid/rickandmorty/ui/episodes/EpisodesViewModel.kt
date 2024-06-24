package com.mohsenoid.rickandmorty.ui.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohsenoid.rickandmorty.domain.RepositoryGetResult
import com.mohsenoid.rickandmorty.domain.episodes.EpisodeRepository
import com.mohsenoid.rickandmorty.domain.episodes.model.Episode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EpisodesViewModel(
    private val episodeRepository: EpisodeRepository,
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
            when (val result = episodeRepository.getEpisodes()) {
                is RepositoryGetResult.Success -> {
                    episodes.clear()
                    episodes += result.data
                    _uiState.value = EpisodesUiState.Success(episodes = episodes)
                }

                is RepositoryGetResult.Failure.EndOfList -> {
                    // Unexpected end of list!
                    _uiState.value = EpisodesUiState.Error.Unknown(result.message)
                }

                is RepositoryGetResult.Failure.NoConnection -> {
                    _uiState.value = EpisodesUiState.Error.NoConnection
                }

                is RepositoryGetResult.Failure.Unknown -> {
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
                when (val result = episodeRepository.getEpisodes(page)) {
                    is RepositoryGetResult.Success -> {
                        currentPage++
                        episodes += result.data
                        EpisodesUiState.Success(episodes = episodes)
                    }

                    is RepositoryGetResult.Failure.EndOfList -> {
                        EpisodesUiState.Success(episodes = episodes, isEndOfList = true)
                    }

                    is RepositoryGetResult.Failure.NoConnection -> {
                        EpisodesUiState.Success(episodes = episodes, isNoConnectionError = true)
                    }

                    is RepositoryGetResult.Failure.Unknown -> {
                        EpisodesUiState.Success(episodes = episodes, error = result.message)
                    }
                }
        }
    }
}