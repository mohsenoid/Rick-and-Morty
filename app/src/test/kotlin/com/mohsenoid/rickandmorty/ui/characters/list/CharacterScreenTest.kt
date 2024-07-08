package com.mohsenoid.rickandmorty.ui.characters.list

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.mohsenoid.rickandmorty.util.createCharacter
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import kotlin.test.Test

@RunWith(RobolectricTestRunner::class)
class CharacterScreenTest {
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
    fun `When content is CharacterItem, Then it should display Character id and name`() {
        // GIVEN

        // WHEN
        rule.setContent {
            CharacterItem(
                character =
                    createCharacter(
                        id = TEST_CHARACTER_ID,
                        name = TEST_CHARACTER_NAME,
                    ),
            )
        }

        // THEN
        rule.onNodeWithText("#" + TEST_CHARACTER_ID).assertExists()
        rule.onNodeWithText(TEST_CHARACTER_NAME).assertExists()
    }

    companion object {
        private const val TEST_CHARACTER_ID = 0
        private const val TEST_CHARACTER_NAME = "Rick Sanchez"
    }
}
