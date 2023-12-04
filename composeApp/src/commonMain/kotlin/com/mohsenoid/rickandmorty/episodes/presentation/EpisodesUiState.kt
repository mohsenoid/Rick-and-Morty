package com.mohsenoid.rickandmorty.episodes.presentation

import com.mohsenoid.rickandmorty.episodes.domain.model.Episode

sealed interface EpisodesUiState {
    data object Loading : EpisodesUiState
    data class Success(val episodes: List<Episode>) : EpisodesUiState
    data class Error(val message: String) : EpisodesUiState
}
