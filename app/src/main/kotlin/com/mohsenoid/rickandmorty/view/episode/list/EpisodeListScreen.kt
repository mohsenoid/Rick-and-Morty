package com.mohsenoid.rickandmorty.view.episode.list

import androidx.compose.runtime.Composable
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mohsenoid.rickandmorty.view.RickAndMortyTheme

@Composable
fun EpisodeListScreen(viewModel: EpisodeListViewModel) {
    val uiState = viewModel.uiState
    RickAndMortyTheme(darkTheme = true) {
        val isRefreshing = uiState.value.isRefreshing

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { viewModel.loadEpisodes() },
        ) {
            EpisodeList(episodes = uiState.value.episodes) {
                viewModel.loadMoreEpisodes()
            }
        }
    }
}
