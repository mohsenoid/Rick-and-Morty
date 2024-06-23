package com.mohsenoid.rickandmorty.ui.episodes

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.mohsenoid.rickandmorty.util.createEpisode
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class EpisodeScreenTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun `When content is EpisodeItem, Then it should display EpisodeItem name, airDate and episode`() {
        // GIVEN

        // WHEN
        rule.setContent {
            EpisodeItem(
                episode =
                    createEpisode(
                        name = TEST_EPISODE_NAME,
                        airDate = TEST_EPISODE_AIR_DATE,
                        episode = TEST_EPISODE_EPISODE,
                    ),
            )
        }

        // THEN
        rule.onNodeWithText(TEST_EPISODE_NAME).assertExists()
        rule.onNodeWithText(TEST_EPISODE_AIR_DATE).assertExists()
        rule.onNodeWithText(TEST_EPISODE_EPISODE).assertExists()
    }

    companion object {
        private const val TEST_EPISODE_NAME = "Pilot"
        private const val TEST_EPISODE_AIR_DATE = "December 2, 2013"
        private const val TEST_EPISODE_EPISODE = "S01E01"
    }
}
