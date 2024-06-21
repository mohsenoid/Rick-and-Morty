package com.mohsenoid.rickandmorty.ui.episodes

import com.mohsenoid.rickandmorty.domain.episodes.model.Episode

sealed interface EpisodesUiState {
    data object Loading : EpisodesUiState

    data class Success(
        val episodes: List<Episode>,
        val isLoadingMore: Boolean = false,
        val isNoConnectionError: Boolean = false,
        val error: String? = null,
        val isEndOfList: Boolean = false,
    ) : EpisodesUiState

    sealed interface Error : EpisodesUiState {
        data object NoConnection : Error

        data class Unknown(val message: String) : Error
    }
}
