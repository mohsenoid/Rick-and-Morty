package com.mohsenoid.rickandmorty.character.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mohsenoid.rickandmorty.character.domain.model.Character
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.http.Url

@Composable
fun CharacterScreen(
    uiState: CharacterUiState,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        when (uiState) {
            CharacterUiState.Loading -> {
                AnimatedVisibility(visible = true) {
                    Text(text = "Loading...", style = MaterialTheme.typography.titleLarge)
                }
            }

            is CharacterUiState.Success -> {
                AnimatedVisibility(visible = true) {
                    CharacterDetails(
                        character = uiState.character,
                    )
                }
            }

            is CharacterUiState.Error -> {
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
fun CharacterDetails(
    character: Character,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier.fillMaxWidth(),
    ) {
        Box {
            KamelImage(
                resource = asyncPainterResource(data = Url(character.image)),
                modifier = Modifier.fillMaxWidth().aspectRatio(1f),
                contentDescription = character.name,
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f))
                    .align(Alignment.BottomCenter)
                    .padding(8.dp),
            ) {
                Text(
                    text = character.name,
                    modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = character.created,
                    modifier = Modifier.fillMaxWidth().padding(start = 0.dp, top = 4.dp, end = 0.dp, bottom = 4.dp),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
        Column(
            modifier = Modifier.padding(8.dp).fillMaxWidth(),
        ) {

            Text(
                text = character.status,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}
