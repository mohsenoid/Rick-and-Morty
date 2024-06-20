package com.mohsenoid.rickandmorty.ui.characters.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.mohsenoid.rickandmorty.domain.characters.model.Character
import com.mohsenoid.rickandmorty.ui.LoadingScreen
import com.mohsenoid.rickandmorty.ui.NoConnectionErrorScreen
import com.mohsenoid.rickandmorty.ui.UnknownErrorScreen
import com.mohsenoid.rickandmorty.ui.theme.RickAndMortyTheme
import com.mohsenoid.rickandmorty.ui.util.AsyncImageWithPreview
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailsScreen(
    characterId: Int,
    modifier: Modifier = Modifier,
) {
    val viewModel: CharacterDetailsViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.loadCharacter(characterId)
    }

    val pullToRefreshState = rememberPullToRefreshState()
    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(Unit) {
            viewModel.loadCharacter(characterId)
            // Fail safety timeout
            @Suppress("MagicNumber")
            delay(500)
            if (!uiState.isLoading) {
                pullToRefreshState.endRefresh()
            }
        }
    }

    if (!uiState.isLoading) {
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
        if (uiState.isLoading) {
            LoadingScreen()
        } else if (uiState.isNoConnectionError) {
            NoConnectionErrorScreen(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
            )
        } else if (uiState.unknownError != null) {
            UnknownErrorScreen(
                message = uiState.unknownError!!,
                modifier =
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
            )
        } else if (uiState.character == null) {
            UnknownErrorScreen(
                message = "Error!",
                modifier =
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
            )
        } else {
            CharacterDetails(
                character = uiState.character!!,
            )
        }
        PullToRefreshContainer(
            state = pullToRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
        )
    }
}

@Composable
fun CharacterDetails(
    modifier: Modifier = Modifier,
    character: Character,
) {
    Column(modifier = modifier.fillMaxSize()) {
        AsyncImageWithPreview(
            model = character.image,
            contentDescription = character.name,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.inverseOnSurface),
        )
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "#${character.id}",
                modifier =
                    Modifier
                        .fillMaxWidth(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelMedium,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = character.name,
                modifier =
                    Modifier
                        .fillMaxWidth(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.height(16.dp))
            CharacterDetailRow("Species", character.species)
            Spacer(modifier = Modifier.height(8.dp))
            CharacterDetailRow("Origin", character.origin)
            Spacer(modifier = Modifier.height(8.dp))
            CharacterDetailRow("Type", character.type)
            Spacer(modifier = Modifier.height(8.dp))
            CharacterDetailRow("Gender", character.gender)
            Spacer(modifier = Modifier.height(8.dp))
            CharacterDetailRow("Origin", character.origin)
            Spacer(modifier = Modifier.height(8.dp))
            CharacterDetailRow("Location", character.location)
        }
    }
}

@Composable
private fun CharacterDetailRow(
    label: String,
    value: String,
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.labelLarge,
        )
        Text(
            text = value.ifEmpty { "N/A" },
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CharacterDetailsDarkPreview() {
    RickAndMortyTheme(darkTheme = true) {
        CharacterDetails(
            character =
                Character(
                    id = 1,
                    name = "Rick Sanchez",
                    status = "Alive",
                    species = "Human",
                    type = "",
                    gender = "Male",
                    origin = "Earth (C-137)",
                    location = "Citadel of Ricks",
                    image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                ),
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CharacterDetailsPreview() {
    RickAndMortyTheme(darkTheme = false) {
        CharacterDetails(
            character =
                Character(
                    id = 1,
                    name = "Rick Sanchez",
                    status = "Alive",
                    species = "Human",
                    type = "",
                    gender = "Male",
                    origin = "Earth (C-137)",
                    location = "Citadel of Ricks",
                    image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                ),
        )
    }
}
