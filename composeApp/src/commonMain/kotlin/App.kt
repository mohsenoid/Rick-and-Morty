import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import episodes.data.EpisodesRemoteDataSource
import episodes.data.EpisodesRepositoryImpl
import episodes.domain.Episode
import episodes.presentation.EpisodesUiState
import episodes.presentation.EpisodesViewModel
import theme.Theme

@Composable
fun App() {
    Theme(darkTheme = true) {
        Scaffold {
            val episodesViewModel =
                getViewModel(
                    key = Unit,
                    factory = viewModelFactory {
                        EpisodesViewModel(
                            repository = EpisodesRepositoryImpl(remote = EpisodesRemoteDataSource()),
                        )
                    },
                )
            val uiState by episodesViewModel.uiState.collectAsState()
            LaunchedEffect(episodesViewModel) {
                episodesViewModel.updateEpisodes()
            }

            EpisodesPage(
                uiState = uiState,
                modifier = Modifier.fillMaxSize(),
                onEpisodeClicked = episodesViewModel::onEpisodeClicked,
            )
        }
    }
}

@Composable
fun EpisodesPage(
    uiState: EpisodesUiState,
    modifier: Modifier = Modifier,
    onEpisodeClicked: (episodeId: Int) -> Unit,
) {
    Column(modifier = modifier) {
        AnimatedVisibility(visible = uiState.episodes.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
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
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
        ) {
            Text(text = episode.episode, style = MaterialTheme.typography.titleSmall)
            Text(text = episode.name, style = MaterialTheme.typography.titleLarge)
            Text(text = episode.airDate, style = MaterialTheme.typography.titleMedium)
        }
    }
}
