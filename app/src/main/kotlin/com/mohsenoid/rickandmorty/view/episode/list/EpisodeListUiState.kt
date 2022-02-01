package com.mohsenoid.rickandmorty.view.episode.list

data class EpisodeListUiState(
    val isRefreshing: Boolean = false,
    val loadingMore: Boolean = false,
    val isOffline: Boolean = false,
    val isEndOfList: Boolean = false,
    val error: Boolean = false,
)
