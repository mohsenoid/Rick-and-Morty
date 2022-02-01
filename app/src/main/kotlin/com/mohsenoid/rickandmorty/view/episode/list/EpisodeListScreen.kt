package com.mohsenoid.rickandmorty.view.episode.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mohsenoid.rickandmorty.view.RickAndMortyTheme

@Composable
fun EpisodeListScreen(viewModel: EpisodeListViewModel) {
    val uiState = viewModel.uiState.collectAsState()
    RickAndMortyTheme(darkTheme = true) {
        val isRefreshing = uiState.value.isRefreshing

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { /*viewModel.loadEpisodes()*/ },
        ) {
            EpisodeList(episodes = viewModel.episodes) { characterIds ->
                viewModel.onEpisodeSelected(characterIds)
            }
        }
    }
}
