package com.mohsenoid.rickandmorty.view.episode.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohsenoid.rickandmorty.view.RickAndMortyTheme
import com.mohsenoid.rickandmorty.view.model.ViewEpisodeItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EpisodeRow(item: ViewEpisodeItem) {
    Card(
        onClick = item.onClick,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
        ) {
            Text(text = item.episode, style = MaterialTheme.typography.subtitle1)
            Text(text = item.name, style = MaterialTheme.typography.h6)
            Text(text = item.airDate, style = MaterialTheme.typography.caption)
        }
    }
}

private val previewEpisode = ViewEpisodeItem(
    name = "Pilot",
    airDate = "December 2, 2013",
    episode = "S01E01",
    onClick = {},
)

@Preview
@Composable
private fun PreviewDarkEpisodeRow() {
    RickAndMortyTheme(darkTheme = true) {
        EpisodeRow(previewEpisode)
    }
}

@Preview
@Composable
private fun PreviewLightEpisodeRow() {
    RickAndMortyTheme(darkTheme = false) {
        EpisodeRow(previewEpisode)
    }
}
