package com.mohsenoid.rickandmorty.episode.presentation

import com.mohsenoid.rickandmorty.episode.domain.model.Episode

sealed interface EpisodeUiState {
    data object Loading : EpisodeUiState
    data class Success(val episode: Episode) : EpisodeUiState
    data class Error(val message: String) : EpisodeUiState
}
