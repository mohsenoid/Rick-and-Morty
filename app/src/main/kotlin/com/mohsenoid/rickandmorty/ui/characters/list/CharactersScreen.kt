package com.mohsenoid.rickandmorty.ui.characters.list

import android.content.res.Configuration
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mohsenoid.rickandmorty.domain.characters.model.Character
import com.mohsenoid.rickandmorty.ui.LoadingScreen
import com.mohsenoid.rickandmorty.ui.NavRoute
import com.mohsenoid.rickandmorty.ui.NoConnectionErrorScreen
import com.mohsenoid.rickandmorty.ui.UnknownErrorScreen
import com.mohsenoid.rickandmorty.ui.theme.RickAndMortyTheme
import com.mohsenoid.rickandmorty.ui.util.AsyncImageWithPreview
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersScreen(
    navController: NavHostController,
    charactersIds: Set<Int>,
    modifier: Modifier = Modifier,
) {
    val viewModel: CharactersViewModel = koinViewModel { parametersOf(charactersIds) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.loadCharacters()
    }

    val pullToRefreshState = rememberPullToRefreshState()
    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(Unit) {
            viewModel.loadCharacters()
            // Fail safety timeout
            @Suppress("MagicNumber")
            delay(500)
            if (uiState !is CharactersUiState.Loading) {
                pullToRefreshState.endRefresh()
            }
        }
    }

    if (uiState !is CharactersUiState.Loading) {
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
            CharactersUiState.Loading -> {
                LoadingScreen()
            }

            CharactersUiState.Error.NoConnection -> {
                NoConnectionErrorScreen(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                )
            }

            is CharactersUiState.Error.Unknown -> {
                UnknownErrorScreen(
                    message = currentUiState.message,
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                )
            }

            is CharactersUiState.Success -> {
                CharactersList(
                    onCharacterClicked = { character ->
                        navController.navigate(NavRoute.CharacterDetailsScreen.endpoint + character.id)
                    },
                    characters = currentUiState.characters,
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
fun CharactersList(
    modifier: Modifier = Modifier,
    onCharacterClicked: (Character) -> Unit = {},
    characters: List<Character>,
) {
    LazyColumn(
        modifier
            .fillMaxSize()
            .padding(8.dp),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(characters.toList(), key = Character::id) { character ->
            CharacterItem(character, onCharacterClicked)
        }
    }
}

@VisibleForTesting
@Composable
fun CharacterItem(
    character: Character,
    onCharacterClicked: (Character) -> Unit = {},
) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable { onCharacterClicked(character) },
        colors =
            CardDefaults.cardColors(
                containerColor =
                    if (!character.isAlive || character.isKilled) {
                        MaterialTheme.colorScheme.error
                    } else {
                        Color.Unspecified
                    },
            ),
    ) {
        Row(modifier = Modifier.height(80.dp)) {
            AsyncImageWithPreview(
                model = character.image,
                contentDescription = character.name,
                modifier =
                    Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f),
            )

            Column(
                modifier =
                    Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(8.dp),
            ) {
                Text(
                    text = "#${character.id}",
                    modifier =
                        Modifier
                            .fillMaxWidth(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelMedium,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = character.name,
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

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CharacterItemPreview() {
    RickAndMortyTheme {
        CharacterItem(
            Character(
                id = 1,
                name = "Rick Sanchez",
                isAlive = true,
                isKilled = false,
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

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CharacterItemDeadPreview() {
    RickAndMortyTheme {
        CharacterItem(
            Character(
                id = 1,
                name = "Rick Sanchez",
                isAlive = false,
                isKilled = false,
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

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CharactersListPreview() {
    RickAndMortyTheme {
        CharactersList(
            characters =
                listOf(
                    Character(
                        id = 1,
                        name = "Rick Sanchez",
                        isAlive = true,
                        isKilled = false,
                        species = "Human",
                        type = "",
                        gender = "Male",
                        origin = "Earth (C-137)",
                        location = "Citadel of Ricks",
                        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                    ),
                    Character(
                        id = 2,
                        name = "Morty Smith",
                        isAlive = false,
                        isKilled = false,
                        species = "Human",
                        type = "",
                        gender = "Male",
                        origin = "unknown",
                        location = "Citadel of Ricks",
                        image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
                    ),
                ),
        )
    }
}
