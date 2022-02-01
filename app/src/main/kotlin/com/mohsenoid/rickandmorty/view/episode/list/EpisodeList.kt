package com.mohsenoid.rickandmorty.view.episode.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.mohsenoid.rickandmorty.view.model.ViewEpisodeItem
import kotlinx.coroutines.flow.Flow

@Composable
fun EpisodeList(
    episodes: Flow<PagingData<ViewEpisodeItem>>,
    onEpisodeClick: (List<Int>) -> Unit,
) {
    val episodeListItems: LazyPagingItems<ViewEpisodeItem> = episodes.collectAsLazyPagingItems()

    LazyColumn {
        items(episodeListItems) { episode ->
            episode?.let {
                EpisodeRow(episode) {
                    onEpisodeClick(episode.characterIds)
                }
            }
        }
    }
}

// private val previewEpisodes = listOf(
//     ViewEpisodeItem(
//         characterIds = listOf(1, 2),
//         name = "Pilot",
//         airDate = "December 2, 2013",
//         episode = "S01E01",
//     ),
//     ViewEpisodeItem(
//         characterIds = listOf(1, 3),
//         name = "Pilot2",
//         airDate = "December 9, 2013",
//         episode = "S01E02",
//     ),
// )

// @Preview
// @Composable
// private fun PreviewDarkEpisodeList() {
//     RickAndMortyTheme(darkTheme = true) {
//         EpisodeList(previewEpisodes)
//     }
// }

// @Preview
// @Composable
// private fun PreviewLightEpisodeList() {
//     RickAndMortyTheme(darkTheme = false) {
//         EpisodeList(previewEpisodes)
//     }
// }
