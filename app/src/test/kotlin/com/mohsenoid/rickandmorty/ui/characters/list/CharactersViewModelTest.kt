package com.mohsenoid.rickandmorty.ui.characters.list

import com.mohsenoid.rickandmorty.domain.characters.usecase.GetCharactersUseCase
import com.mohsenoid.rickandmorty.util.MainDispatcherRule
import com.mohsenoid.rickandmorty.util.createCharactersList
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class CharactersViewModelTest {
    @get:Rule
    var rule: TestRule = MainDispatcherRule()

    private lateinit var getCharactersUseCase: GetCharactersUseCase

    private lateinit var viewModel: CharactersViewModel

    @Before
    fun setUp() {
        getCharactersUseCase = mockk()
        viewModel =
            CharactersViewModel(
                charactersIds = TEST_CHARACTERS_IDS,
                getCharactersUseCase = getCharactersUseCase,
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
    fun `Given GetCharactersUseCase returns Success, When loadEpisodes called, Then UiState should be Success`() {
        // GIVEN
        val characters = createCharactersList(3).toSet()
        coEvery {
            getCharactersUseCase(TEST_CHARACTERS_IDS)
        } returns GetCharactersUseCase.Result.Success(characters)

        // WHEN
        viewModel.loadCharacters()
        val actualUiState = viewModel.uiState.value
        val expectedUiState = CharactersUiState.Success(characters = characters)

        // THEN
        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `Given GetCharactersUseCase returns NoConnection, When loadEpisodes called, Then UiState should be no connection error`() {
        // GIVEN
        coEvery {
            getCharactersUseCase(TEST_CHARACTERS_IDS)
        } returns GetCharactersUseCase.Result.NoConnection

        // WHEN
        viewModel.loadCharacters()
        val actualUiState = viewModel.uiState.value
        val expectedUiState = CharactersUiState.Error.NoConnection

        // THEN
        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `Given GetCharactersUseCase returns Failure, When loadEpisodes called, Then UiState should be unknown error`() {
        // GIVEN
        val errorMessage = "Unknown error"
        coEvery {
            getCharactersUseCase(TEST_CHARACTERS_IDS)
        } returns GetCharactersUseCase.Result.Failure(errorMessage)

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
