package com.mohsenoid.rickandmorty.ui.characters.details

import com.mohsenoid.rickandmorty.domain.RepositoryGetResult
import com.mohsenoid.rickandmorty.domain.characters.CharactersRepository
import com.mohsenoid.rickandmorty.util.MainDispatcherRule
import com.mohsenoid.rickandmorty.util.createCharacter
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class CharacterDetailsViewModelTest {
    @get:Rule
    var rule: TestRule = MainDispatcherRule()

    private lateinit var repository: CharactersRepository

    private lateinit var viewModel: CharacterDetailsViewModel

    @Before
    fun setUp() {
        repository = mockk()
        viewModel =
            CharacterDetailsViewModel(
                characterId = TEST_CHARACTER_ID,
                charactersRepository = repository,
            )
    }

    @Test
    fun `When initialized, Then UiState initial value should be Loading`() {
        // GIVEN

        // WHEN
        val actualUiState = viewModel.uiState.value
        val expectedUiState = CharacterDetailsUiState.Loading

        // THEN
        kotlin.test.assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `Given repository returns Success, When loadCharacter called, Then UiState should be Success`() {
        // GIVEN
        val character = createCharacter(id = TEST_CHARACTER_ID)
        coEvery {
            repository.getCharacter(TEST_CHARACTER_ID)
        } returns RepositoryGetResult.Success(character)

        // WHEN
        viewModel.loadCharacter()
        val actualUiState = viewModel.uiState.value
        val expectedUiState = CharacterDetailsUiState.Success(character = character)

        // THEN
        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `Given repository returns no connection, When loadCharacter called, Then UiState should be no connection error`() {
        // GIVEN
        coEvery {
            repository.getCharacter(TEST_CHARACTER_ID)
        } returns RepositoryGetResult.Failure.NoConnection("No connection")

        // WHEN
        viewModel.loadCharacter()
        val actualUiState = viewModel.uiState.value
        val expectedUiState = CharacterDetailsUiState.Error.NoConnection

        // THEN
        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `Given repository returns unknown error, When loadCharacter called, Then UiState should be unknown error`() {
        // GIVEN
        val errorMessage = "Unknown error"
        coEvery {
            repository.getCharacter(TEST_CHARACTER_ID)
        } returns RepositoryGetResult.Failure.Unknown(errorMessage)

        // WHEN
        viewModel.loadCharacter()
        val actualUiState = viewModel.uiState.value
        val expectedUiState = CharacterDetailsUiState.Error.Unknown(errorMessage)

        // THEN
        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `Given repository returns Success, When onKillClicked called, Then UiState should be Success`() {
        // GIVEN
        val character = createCharacter(id = TEST_CHARACTER_ID)
        coEvery {
            repository.getCharacter(TEST_CHARACTER_ID)
        } returns RepositoryGetResult.Success(character)
        coEvery {
            repository.updateCharacterStatus(TEST_CHARACTER_ID, true)
        } returns RepositoryGetResult.Success(character)

        // WHEN
        viewModel.loadCharacter()
        viewModel.onKillClicked()
        val actualUiState = viewModel.uiState.value
        val expectedUiState = CharacterDetailsUiState.Success(character = character)

        // THEN
        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `Given repository returns no connection, When onKillClicked called, Then UiState should be no connection error`() {
        // GIVEN
        val character = createCharacter(id = TEST_CHARACTER_ID)
        coEvery {
            repository.getCharacter(TEST_CHARACTER_ID)
        } returns RepositoryGetResult.Success(character)
        coEvery {
            repository.updateCharacterStatus(TEST_CHARACTER_ID, true)
        } returns RepositoryGetResult.Failure.NoConnection("No connection")

        // WHEN
        viewModel.loadCharacter()
        viewModel.onKillClicked()
        val actualUiState = viewModel.uiState.value
        val expectedUiState = CharacterDetailsUiState.Error.NoConnection

        // THEN
        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `Given repository returns unknown error, When onKillClicked called, Then UiState should be unknown error`() {
        // GIVEN
        val character = createCharacter(id = TEST_CHARACTER_ID)
        coEvery {
            repository.getCharacter(TEST_CHARACTER_ID)
        } returns RepositoryGetResult.Success(character)
        val errorMessage = "Unknown error"
        coEvery {
            repository.updateCharacterStatus(TEST_CHARACTER_ID, true)
        } returns RepositoryGetResult.Failure.Unknown(errorMessage)

        // WHEN
        viewModel.loadCharacter()
        viewModel.onKillClicked()
        val actualUiState = viewModel.uiState.value
        val expectedUiState = CharacterDetailsUiState.Error.Unknown(errorMessage)

        // THEN
        assertEquals(expectedUiState, actualUiState)
    }

    companion object {
        private const val TEST_CHARACTER_ID = 1
    }
}
