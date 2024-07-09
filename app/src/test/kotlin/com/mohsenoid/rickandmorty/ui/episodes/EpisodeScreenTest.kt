package com.mohsenoid.rickandmorty.ui.episodes

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.mohsenoid.rickandmorty.util.createEpisode
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import kotlin.test.Test

@RunWith(RobolectricTestRunner::class)
class EpisodeScreenTest {
    @get:Rule
    val rule = createComposeRule()

    @Before
    fun setUp() {
        stopKoin() // To fix KoinAppAlreadyStartedException
    }

    @After
    fun tearDown() {
        stopKoin() // To fix KoinAppAlreadyStartedException
    }

    @Test
    fun `When content is EpisodeItem, Then it should display episode name, airDate, and episode`() {
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
