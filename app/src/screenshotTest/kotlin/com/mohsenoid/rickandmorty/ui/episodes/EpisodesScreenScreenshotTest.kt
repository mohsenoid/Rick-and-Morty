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
    fun EpisodesItemPreview() {
        RickAndMortyTheme {
            EpisodeItem(
                episode =
                    Episode(
                        id = 1,
                        name = "Pilot",
                        airDate = "December 2, 2013",
                        episode = "S01E01",
                        characters = setOf(1, 2, 3),
                    ),
            )
        }
    }
}
