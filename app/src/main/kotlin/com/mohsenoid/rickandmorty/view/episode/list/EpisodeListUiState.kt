package com.mohsenoid.rickandmorty.view.episode.list

import com.mohsenoid.rickandmorty.view.model.ViewEpisodeItem

data class EpisodeListUiState(
    val loading: Boolean = false,
    val loadingMore: Boolean = false,
    val isOffline: Boolean = false,
    val isEndOfList: Boolean = false,
    val error: Boolean = false,
    val episodes: List<ViewEpisodeItem> = emptyList(),
)
