package com.mohsenoid.rickandmorty.ui.characters.details

import app.cash.turbine.test
import com.mohsenoid.rickandmorty.domain.characters.usecase.GetCharacterDetailsUseCase
import com.mohsenoid.rickandmorty.domain.characters.usecase.UpdateCharacterStatusUseCase
import com.mohsenoid.rickandmorty.util.MainDispatcherRule
import com.mohsenoid.rickandmorty.util.createCharacter
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class CharacterDetailsViewModelTest {
    @get:Rule
    var rule: TestRule = MainDispatcherRule()

    private lateinit var getCharacterDetailsUseCase: GetCharacterDetailsUseCase
    private lateinit var updateCharacterStatusUseCase: UpdateCharacterStatusUseCase

    private lateinit var viewModel: CharacterDetailsViewModel

    @Before
    fun setUp() {
        getCharacterDetailsUseCase = mockk()
        updateCharacterStatusUseCase = mockk()
        viewModel =
            CharacterDetailsViewModel(
                characterId = TEST_CHARACTER_ID,
                getCharacterDetailsUseCase = getCharacterDetailsUseCase,
                updateCharacterStatusUseCase = updateCharacterStatusUseCase,
            )
    }

    @Test
    fun `When initialized, Then UiState initial value should be Loading`() {
        // GIVEN

        // WHEN
        val actualUiState = viewModel.uiState.value
        val expectedUiState = CharacterDetailsUiState.Loading

        // THEN
        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `Given GetCharacterDetailsUseCase returns Success, When loadCharacter called, Then UiState should be Success`() {
        // GIVEN
        val character = createCharacter(id = TEST_CHARACTER_ID)
        coEvery { getCharacterDetailsUseCase(TEST_CHARACTER_ID) } returns GetCharacterDetailsUseCase.Result.Success(character)

        // WHEN
        viewModel.loadCharacter()
        val actualUiState = viewModel.uiState.value
        val expectedUiState = CharacterDetailsUiState.Success(character = character)

        // THEN
        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `Given GetCharacterDetailsUseCase returns no connection, When loadCharacter called, Then UiState should be no connection error`() {
        // GIVEN
        coEvery { getCharacterDetailsUseCase(TEST_CHARACTER_ID) } returns GetCharacterDetailsUseCase.Result.NoConnection

        // WHEN
        viewModel.loadCharacter()
        val actualUiState = viewModel.uiState.value
        val expectedUiState = CharacterDetailsUiState.Error.NoConnection

        // THEN
        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `Given GetCharacterDetailsUseCase returns unknown error, When loadCharacter called, Then UiState should be unknown error`() {
        // GIVEN
        val errorMessage = "Unknown error"
        coEvery { getCharacterDetailsUseCase(TEST_CHARACTER_ID) } returns GetCharacterDetailsUseCase.Result.Failure(errorMessage)

        // WHEN
        viewModel.loadCharacter()
        val actualUiState = viewModel.uiState.value
        val expectedUiState = CharacterDetailsUiState.Error.Unknown(errorMessage)

        // THEN
        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `Given GetCharacterDetailsUseCase returns Success, When onKillClicked called, Then UiState should be Success`() {
        // GIVEN
        val character = createCharacter(id = TEST_CHARACTER_ID)
        coEvery { getCharacterDetailsUseCase(TEST_CHARACTER_ID) } returns GetCharacterDetailsUseCase.Result.Success(character)
        coEvery { updateCharacterStatusUseCase(TEST_CHARACTER_ID, true) } returns UpdateCharacterStatusUseCase.Result.Success

        // WHEN
        viewModel.loadCharacter()
        viewModel.onKillClicked()
        val actualUiState = viewModel.uiState.value
        val expectedUiState = CharacterDetailsUiState.Success(character = character)

        // THEN
        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `Given UpdateCharacterStatusUseCase returns Failure, When onKillClicked called, Then UiState should still remain success`() {
        // GIVEN
        val character = createCharacter(id = TEST_CHARACTER_ID)
        coEvery { getCharacterDetailsUseCase(TEST_CHARACTER_ID) } returns GetCharacterDetailsUseCase.Result.Success(character)
        coEvery { updateCharacterStatusUseCase(TEST_CHARACTER_ID, true) } returns UpdateCharacterStatusUseCase.Result.Failure

        // WHEN
        viewModel.loadCharacter()
        viewModel.onKillClicked()
        val actualUiState = viewModel.uiState.value
        val expectedUiState = CharacterDetailsUiState.Success(character)

        // THEN
        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `Given UpdateCharacterStatusUseCase returns failure, When onKillClicked called, Then error message should be emitted`() =
        runTest {
            // GIVEN
            val character = createCharacter(id = TEST_CHARACTER_ID)
            coEvery { getCharacterDetailsUseCase(TEST_CHARACTER_ID) } returns GetCharacterDetailsUseCase.Result.Success(character)
            coEvery { updateCharacterStatusUseCase(TEST_CHARACTER_ID, true) } returns UpdateCharacterStatusUseCase.Result.Failure

            viewModel.updateStatusError.test {
                // WHEN
                viewModel.loadCharacter()
                viewModel.onKillClicked()

                // THEN
                assertTrue(awaitItem())
                ensureAllEventsConsumed()
            }
        }

    companion object {
        private const val TEST_CHARACTER_ID = 1
    }
}
