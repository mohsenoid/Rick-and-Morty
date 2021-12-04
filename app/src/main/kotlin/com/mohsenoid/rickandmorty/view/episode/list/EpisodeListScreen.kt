package com.mohsenoid.rickandmorty.view.episode.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohsenoid.rickandmorty.view.model.ViewEpisodeItem

@Preview
@Composable
fun EpisodeListScreen(viewModel: EpisodeListViewModel) {
    val uiState = viewModel.uiState
    LazyColumn {
        items(uiState.value.episodes) {
            EpisodeRow(it)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
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
                .clickable { item.onClick() }
                .padding(8.dp)
                .fillMaxWidth(),
        ) {
            Text(text = item.episode)
            Text(text = item.name)
            Text(text = item.airDate)
        }
    }
}
