package com.mohsenoid.rickandmorty.episode.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.mohsenoid.rickandmorty.episode.domain.model.Character
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.http.Url

@Composable
fun EpisodeScreen(
    uiState: EpisodeUiState,
    modifier: Modifier = Modifier,
    onCharacterClicked: (characterId: Int) -> Unit,
) {
    Column(modifier = modifier) {
        when (uiState) {
            EpisodeUiState.Loading -> {
                AnimatedVisibility(visible = true) {
                    Text(text = "Loading...", style = MaterialTheme.typography.titleLarge)
                }
            }

            is EpisodeUiState.Success -> {
                AnimatedVisibility(visible = true) {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        items(uiState.episode.characters) { character ->
                            CharacterItem(
                                character = character,
                                onCharacterClicked = onCharacterClicked,
                            )
                        }
                    }
                }
            }

            is EpisodeUiState.Error -> {
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
fun CharacterItem(
    character: Character,
    modifier: Modifier = Modifier,
    onCharacterClicked: (characterId: Int) -> Unit,
) {
    Card(
        onClick = { onCharacterClicked(character.id) },
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Row {
            KamelImage(
                resource = asyncPainterResource(data = Url(character.image)),
                modifier = Modifier.size(100.dp, 100.dp),
                contentDescription = character.name,
            )
            Column(
                modifier = Modifier.padding(8.dp).fillMaxWidth(),
            ) {
                Text(
                    text = character.name,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.inverseSurface,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = character.created,
                    modifier = Modifier.fillMaxWidth().padding(start = 0.dp, top = 4.dp, end = 0.dp, bottom = 4.dp),
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = character.status,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}
