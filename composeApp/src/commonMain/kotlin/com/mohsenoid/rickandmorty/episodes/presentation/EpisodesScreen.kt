package com.mohsenoid.rickandmorty.episodes.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mohsenoid.rickandmorty.episodes.domain.model.Episode

@Composable
fun EpisodesScreen(
    uiState: EpisodesUiState,
    modifier: Modifier = Modifier,
    onEpisodeClicked: (episodeId: Int) -> Unit,
) {
    Column(modifier = modifier) {
        when (uiState) {
            EpisodesUiState.Loading -> {
                AnimatedVisibility(visible = true) {
                    Text(text = "Loading...", style = MaterialTheme.typography.titleLarge)
                }
            }

            is EpisodesUiState.Success -> {
                AnimatedVisibility(visible = uiState.episodes.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        items(uiState.episodes) { episode ->
                            EpisodeItem(
                                episode = episode,
                                onEpisodeClicked = onEpisodeClicked,
                            )
                        }
                    }
                }
            }

            is EpisodesUiState.Error -> {
                AnimatedVisibility(visible = true) {
                    Text(
                        text = "Error: ${uiState.message}",
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodeItem(
    episode: Episode,
    modifier: Modifier = Modifier,
    onEpisodeClicked: (episodeId: Int) -> Unit,
) {
    Card(
        onClick = { onEpisodeClicked(episode.id) },
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Column(
            modifier = Modifier.padding(8.dp).fillMaxWidth(),
        ) {
            Text(
                text = episode.episode,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = episode.name,
                modifier = Modifier.fillMaxWidth().padding(start = 0.dp, top = 4.dp, end = 0.dp, bottom = 4.dp),
                color = MaterialTheme.colorScheme.inverseSurface,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = episode.airDate,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}
