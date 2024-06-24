package com.mohsenoid.rickandmorty.ui.characters.details

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohsenoid.rickandmorty.R
import com.mohsenoid.rickandmorty.domain.characters.model.Character
import com.mohsenoid.rickandmorty.ui.LoadingScreen
import com.mohsenoid.rickandmorty.ui.NoConnectionErrorScreen
import com.mohsenoid.rickandmorty.ui.UnknownErrorScreen
import com.mohsenoid.rickandmorty.ui.theme.RickAndMortyTheme
import com.mohsenoid.rickandmorty.ui.util.AsyncImageWithPreview
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailsScreen(
    characterId: Int,
    modifier: Modifier = Modifier,
) {
    val viewModel: CharacterDetailsViewModel = koinViewModel { parametersOf(characterId) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.loadCharacter()
    }

    val pullToRefreshState = rememberPullToRefreshState()
    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(Unit) {
            viewModel.loadCharacter()
            // Fail safety timeout
            @Suppress("MagicNumber")
            delay(500)
            if (uiState !is CharacterDetailsUiState.Loading) {
                pullToRefreshState.endRefresh()
            }
        }
    }

    if (uiState !is CharacterDetailsUiState.Loading) {
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
            CharacterDetailsUiState.Loading -> {
                LoadingScreen()
            }

            CharacterDetailsUiState.Error.NoConnection -> {
                NoConnectionErrorScreen(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                )
            }

            is CharacterDetailsUiState.Error.Unknown -> {
                UnknownErrorScreen(
                    message = currentUiState.message,
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                )
            }

            is CharacterDetailsUiState.Success -> {
                CharacterDetails(
                    character = currentUiState.character,
                    onKillClicked = { viewModel.onKillClicked() },
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
fun CharacterDetails(
    character: Character,
    modifier: Modifier = Modifier,
    onKillClicked: () -> Unit = {},
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
        Column(
            modifier =
                Modifier
                    .padding(16.dp)
                    .weight(1f),
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
        CharacterStatusButton(
            isAlive = character.isAlive,
            isKilled = character.isKilled,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onKillClicked = onKillClicked,
        )
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

@Composable
private fun CharacterStatusButton(
    isAlive: Boolean,
    isKilled: Boolean,
    modifier: Modifier = Modifier,
    onKillClicked: () -> Unit = {},
) {
    val status =
        when {
            isKilled -> "Status Killed!"
            isAlive -> "Status Alive"
            else -> "Status Dead"
        }
    val text =
        when {
            isKilled -> "Heal me!"
            isAlive -> "Kill me!"
            else -> "N/A"
        }
    val resourceId = if (isKilled || !isAlive) R.drawable.ic_dead else R.drawable.ic_alive
    val tint =
        if (isKilled || !isAlive) MaterialTheme.colorScheme.error else LocalContentColor.current
    val clickableModifier =
        if (isKilled || isAlive) {
            Modifier.clickable(onClick = onKillClicked)
        } else {
            Modifier
        }
    StatusButton(
        status = status,
        text = text,
        resourceId = resourceId,
        tint = tint,
        modifier =
            modifier
                .then(clickableModifier)
                .padding(8.dp),
    )
}

@Composable
private fun StatusButton(
    status: String,
    text: String,
    @DrawableRes resourceId: Int,
    tint: Color,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = status,
            color = tint,
            style = MaterialTheme.typography.labelLarge,
        )
        Icon(
            painter = painterResource(id = resourceId),
            contentDescription = status,
            tint = tint,
        )
        Text(
            text = text,
            color = tint,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CharacterStatusButtonAlivePreview() {
    RickAndMortyTheme {
        CharacterStatusButton(isAlive = true, isKilled = false)
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CharacterStatusButtonDeadPreview() {
    RickAndMortyTheme {
        CharacterStatusButton(isAlive = false, isKilled = false)
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CharacterStatusButtonAliveButKilledPreview() {
    RickAndMortyTheme {
        CharacterStatusButton(isAlive = true, isKilled = true)
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CharacterStatusButtonDeadButHealedPreview() {
    RickAndMortyTheme {
        CharacterStatusButton(isAlive = false, isKilled = false)
    }
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CharacterDetailsPreview() {
    RickAndMortyTheme {
        CharacterDetails(
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
