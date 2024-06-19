package com.mohsenoid.rickandmorty.ui.episodes

import com.mohsenoid.rickandmorty.domain.episodes.model.Episode

data class EpisodesUiState(
    val isLoading: Boolean = false,
    val episodes: List<Episode> = emptyList(),
    val isNoConnectionError: Boolean = false,
    val unknownError: String? = null,
    val isLoadingMore: Boolean = false,
    val isLoadMoreNoConnectionError: Boolean = false,
    val moreError: String? = null,
    val isEndOfList: Boolean = false,
)
