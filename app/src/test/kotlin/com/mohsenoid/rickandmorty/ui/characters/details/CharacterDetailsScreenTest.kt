package com.mohsenoid.rickandmorty.ui.characters.details

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.mohsenoid.rickandmorty.util.createCharacter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import kotlin.test.Test

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class CharacterDetailsScreenTest {
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
    fun `When content is CharacterDetails, Then it should display Character id, name, species, gender and location`() {
        // GIVEN

        // WHEN
        rule.setContent {
            CharacterDetails(
                character =
                    createCharacter(
                        id = TEST_CHARACTER_ID,
                        name = TEST_CHARACTER_NAME,
                        species = TEST_CHARACTER_SPECIES,
                        gender = TEST_CHARACTER_GENDER,
                        location = TEST_CHARACTER_LOCATION,
                    ),
            )
        }

        // THEN
        rule.onNodeWithText("#" + TEST_CHARACTER_ID).assertExists()
        rule.onNodeWithText(TEST_CHARACTER_NAME).assertExists()
        rule.onNodeWithText(TEST_CHARACTER_SPECIES).assertExists()
        rule.onNodeWithText(TEST_CHARACTER_GENDER).assertExists()
        rule.onNodeWithText(TEST_CHARACTER_LOCATION).assertExists()
    }

    companion object {
        private const val TEST_CHARACTER_ID = 0
        private const val TEST_CHARACTER_NAME = "Rick Sanchez"
        private const val TEST_CHARACTER_SPECIES = "Human"
        private const val TEST_CHARACTER_GENDER = "Male"
        private const val TEST_CHARACTER_LOCATION = "Citadel of Ricks"
    }
}
