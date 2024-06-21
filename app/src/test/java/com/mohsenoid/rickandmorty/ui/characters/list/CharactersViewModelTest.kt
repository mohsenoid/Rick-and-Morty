package com.mohsenoid.rickandmorty.ui.characters.list

import com.mohsenoid.rickandmorty.domain.RepositoryGetResult
import com.mohsenoid.rickandmorty.domain.characters.CharactersRepository
import com.mohsenoid.rickandmorty.util.MainDispatcherRule
import com.mohsenoid.rickandmorty.util.createCharactersList
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class CharactersViewModelTest {
    @get:Rule
    var rule: TestRule = MainDispatcherRule()

    private lateinit var repository: CharactersRepository

    private lateinit var viewModel: CharactersViewModel

    @Before
    fun setUp() {
        repository = mockk()
        viewModel =
            CharactersViewModel(
                charactersIds = TEST_CHARACTERS_IDS,
                charactersRepository = repository,
            )
    }

    @Test
    fun `When initialized, Then UiState initial value should be Loading`() {
        // GIVEN

        // WHEN
        val actualUiState = viewModel.uiState.value
        val expectedUiState = CharactersUiState.Loading

        // THEN
        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `Given repository returns Success, When loadEpisodes called, Then UiState should be Success`() {
        // GIVEN
        val characters = createCharactersList(3).toSet()
        coEvery {
            repository.getCharacters(TEST_CHARACTERS_IDS)
        } returns RepositoryGetResult.Success(characters)

        // WHEN
        viewModel.loadCharacters()
        val actualUiState = viewModel.uiState.value
        val expectedUiState = CharactersUiState.Success(characters = characters)

        // THEN
        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `Given repository returns no connection, When loadEpisodes called, Then UiState should be no connection error`() {
        // GIVEN
        coEvery {
            repository.getCharacters(TEST_CHARACTERS_IDS)
        } returns RepositoryGetResult.Failure.NoConnection("No connection")

        // WHEN
        viewModel.loadCharacters()
        val actualUiState = viewModel.uiState.value
        val expectedUiState = CharactersUiState.Error.NoConnection

        // THEN
        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `Given repository returns unknown error, When loadEpisodes called, Then UiState should be unknown error`() {
        // GIVEN
        val errorMessage = "Unknown error"
        coEvery {
            repository.getCharacters(TEST_CHARACTERS_IDS)
        } returns RepositoryGetResult.Failure.Unknown(errorMessage)

        // WHEN
        viewModel.loadCharacters()
        val actualUiState = viewModel.uiState.value
        val expectedUiState = CharactersUiState.Error.Unknown(errorMessage)

        // THEN
        assertEquals(expectedUiState, actualUiState)
    }

    companion object {
        private val TEST_CHARACTERS_IDS: Set<Int> = setOf(1, 2, 3)
    }
}
