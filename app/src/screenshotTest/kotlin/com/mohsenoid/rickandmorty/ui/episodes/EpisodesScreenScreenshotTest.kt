package com.mohsenoid.rickandmorty.ui.episodes

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mohsenoid.rickandmorty.domain.episodes.model.Episode
import com.mohsenoid.rickandmorty.ui.theme.RickAndMortyTheme

class EpisodesScreenScreenshotTest {
    @Suppress("MagicNumber")
    @Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
    @Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
    @Composable
    fun EpisodesItemTest() {
        RickAndMortyTheme {
            EpisodeItem(
                episode =
                    Episode(
                        id = 1,
                        name = "Pilot2",
                        airDate = "December 14, 2013",
                        episode = "S01E01",
                        characters = setOf(1, 2, 3),
                    ),
            )
        }
    }

    @Suppress("MagicNumber")
    @Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
    @Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
    @Composable
    fun EpisodesListTest() {
        RickAndMortyTheme {
            EpisodesList(
                isLoadingMore = true,
                isEndOfList = false,
                episodes =
                    listOf(
                        Episode(
                            id = 1,
                            name = "Pilot2",
                            airDate = "December 4, 2013",
                            episode = "S01E01",
                            characters = setOf(1, 2, 3),
                        ),
                        Episode(
                            id = 2,
                            name = "Lawnmower Dog",
                            airDate = "December 9, 2013",
                            episode = "S01E02",
                            characters = setOf(1, 2, 5, 6),
                        ),
                    ),
            )
        }
    }
}
