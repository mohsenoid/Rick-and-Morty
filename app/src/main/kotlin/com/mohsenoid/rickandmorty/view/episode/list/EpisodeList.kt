package com.mohsenoid.rickandmorty.view.episode.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.mohsenoid.rickandmorty.view.RickAndMortyTheme
import com.mohsenoid.rickandmorty.view.model.ViewEpisodeItem

@Composable
fun EpisodeList(episodes: List<ViewEpisodeItem>, loadMore: () -> Unit) {
    val listState = rememberLazyListState()

    LazyColumn(state = listState) {
        items(episodes) { episode ->
            EpisodeRow(episode)
        }
    }

    val lastItemIndex = listState.layoutInfo.visibleItemsInfo.map { it.index }.lastOrNull()
    val totalItems = episodes.size

    if (lastItemIndex != null && lastItemIndex < totalItems - 4) {
        loadMore()
    }

    Text(
        "${listState.layoutInfo.visibleItemsInfo.map { it.index }}",
        fontSize = 18.sp,
        color = Color.Red,
    )
}

private val previewEpisodes = listOf(
    ViewEpisodeItem(
        name = "Pilot",
        airDate = "December 2, 2013",
        episode = "S01E01",
        onClick = {},
    ),
    ViewEpisodeItem(
        name = "Pilot2",
        airDate = "December 9, 2013",
        episode = "S01E02",
        onClick = {},
    ),
)

@Preview
@Composable
private fun PreviewDarkEpisodeList() {
    RickAndMortyTheme(darkTheme = true) {
        EpisodeList(previewEpisodes) {}
    }
}

@Preview
@Composable
private fun PreviewLightEpisodeList() {
    RickAndMortyTheme(darkTheme = false) {
        EpisodeList(previewEpisodes) {}
    }
}
