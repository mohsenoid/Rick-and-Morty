package com.mohsenoid.rickandmorty.ui.episodes

import android.content.res.Configuration
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mohsenoid.rickandmorty.domain.episodes.model.Episode
import com.mohsenoid.rickandmorty.ui.LoadingScreen
import com.mohsenoid.rickandmorty.ui.NavRoute
import com.mohsenoid.rickandmorty.ui.NoConnectionErrorScreen
import com.mohsenoid.rickandmorty.ui.UnknownErrorScreen
import com.mohsenoid.rickandmorty.ui.theme.RickAndMortyTheme
import com.mohsenoid.rickandmorty.ui.util.EndlessLazyColumn
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodesScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val viewModel: EpisodesViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadEpisodes()
    }

    val pullToRefreshState = rememberPullToRefreshState()
    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(Unit) {
            viewModel.loadEpisodes()
            // Fail safety timeout
            @Suppress("MagicNumber")
            delay(500)
            if (uiState !is EpisodesUiState.Loading) {
                pullToRefreshState.endRefresh()
            }
        }
    }

    if (uiState !is EpisodesUiState.Loading) {
        LaunchedEffect(Unit) {
            pullToRefreshState.endRefresh()
        }
    }

    Box(
        modifier =
            modifier
                .fillMaxSize()
                .nestedScroll(pullToRefreshState.nestedScrollConnection),
    ) {
        when (val currentUiState = uiState) {
            EpisodesUiState.Loading -> {
                LoadingScreen()
            }

            EpisodesUiState.Error.NoConnection -> {
                NoConnectionErrorScreen(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                )
            }

            is EpisodesUiState.Error.Unknown -> {
                UnknownErrorScreen(
                    message = currentUiState.message,
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                )
            }

            is EpisodesUiState.Success -> {
                EpisodesList(
                    isNoConnectionError = currentUiState.isNoConnectionError,
                    isLoadingMore = currentUiState.isLoadingMore,
                    isEndOfList = currentUiState.isEndOfList,
                    onEndOfListReached = viewModel::loadMoreEpisodes,
                    onEpisodeClicked = { episode ->
                        val charactersArg = episode.characters.joinToString(",")
                        navController.navigate(NavRoute.CharactersScreen.endpoint + charactersArg)
                    },
                    episodes = currentUiState.episodes,
                )
            }
        }
        PullToRefreshContainer(
            state = pullToRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
        )
    }
}

@Composable
private fun EpisodesList(
    modifier: Modifier = Modifier,
    isNoConnectionError: Boolean = false,
    isLoadingMore: Boolean = false,
    isEndOfList: Boolean = false,
    onEndOfListReached: () -> Unit = {},
    onEpisodeClicked: (Episode) -> Unit = {},
    episodes: List<Episode>,
) {
    EndlessLazyColumn(
        modifier =
            modifier
                .fillMaxSize()
                .padding(8.dp),
        isNoConnectionError = isNoConnectionError,
        isLoading = isLoadingMore,
        isEndOfList = isEndOfList,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        items = episodes,
        itemKey = Episode::id,
        loadMore = onEndOfListReached,
        noConnectionItem = {
            Icon(
                imageVector = Icons.Filled.WifiOff,
                contentDescription = "No Connection Error",
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.error,
            )
        },
        loadingItem = {
            CircularProgressIndicator(Modifier.size(48.dp))
        },
        itemContent = { episode ->
            EpisodeItem(episode, onEpisodeClicked)
        },
    )
}

@VisibleForTesting
@Composable
fun EpisodeItem(
    episode: Episode,
    onEpisodeClicked: (Episode) -> Unit = {},
) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable { onEpisodeClicked(episode) },
    ) {
        Row(modifier = Modifier.height(80.dp)) {
            Box(
                modifier =
                    Modifier
                        .fillMaxHeight()
                        .width(100.dp)
                        .background(MaterialTheme.colorScheme.inverseOnSurface),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = episode.episode,
                    style = MaterialTheme.typography.titleLarge,
                )
            }

            Column(
                modifier =
                    Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(8.dp),
            ) {
                Text(
                    text = episode.airDate,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelMedium,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = episode.name,
                    modifier =
                        Modifier
                            .fillMaxWidth(),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}

@Suppress("MagicNumber")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun EpisodesItemPreview() {
    RickAndMortyTheme {
        EpisodeItem(
            episode =
                Episode(
                    id = 1,
                    name = "Pilot",
                    airDate = "December 2, 2013",
                    episode = "S01E01",
                    characters = setOf(1, 2, 3),
                ),
        )
    }
}

@Suppress("MagicNumber")
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun EpisodesListPreview() {
    RickAndMortyTheme {
        EpisodesList(
            isLoadingMore = true,
            isEndOfList = false,
            episodes =
                listOf(
                    Episode(
                        id = 1,
                        name = "Pilot",
                        airDate = "December 2, 2013",
                        episode = "S01E01",
                        characters = setOf(1, 2, 3),
                    ),
                    Episode(
                        id = 2,
                        name = "Lawnmower Dog",
                        airDate = "December 9, 2013",
                        episode = "S01E02",
                        characters = setOf(1, 2, 5, 6),
                    ),
                ),
        )
    }
}
