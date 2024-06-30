package com.mohsenoid.rickandmorty.ui.episodes

import com.mohsenoid.rickandmorty.domain.episodes.usecase.GetEpisodesUseCase
import com.mohsenoid.rickandmorty.util.MainDispatcherRule
import com.mohsenoid.rickandmorty.util.createEpisodesList
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class EpisodesViewModelTest {
    @get:Rule
    var rule: TestRule = MainDispatcherRule()

    private lateinit var getEpisodesUseCase: GetEpisodesUseCase

    private lateinit var viewModel: EpisodesViewModel

    @Before
    fun setUp() {
        getEpisodesUseCase = mockk()
        viewModel = EpisodesViewModel(getEpisodesUseCase = getEpisodesUseCase)
    }

    @Test
    fun `When initialized, Then UiState initial value should be Loading`() {
        // GIVEN

        // WHEN
        val actualUiState = viewModel.uiState.value
        val expectedUiState = EpisodesUiState.Loading

        // THEN
        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `Given GetEpisodesUseCase returns Success, When loadEpisodes called, Then UiState should be Success`() {
        // GIVEN
        val episodes = createEpisodesList(3)
        coEvery { getEpisodesUseCase(0) } returns GetEpisodesUseCase.Result.Success(episodes)

        // WHEN
        viewModel.loadEpisodes()
        val actualUiState = viewModel.uiState.value
        val expectedUiState = EpisodesUiState.Success(episodes = episodes)

        // THEN
        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `Given GetEpisodesUseCase returns end of list, When loadMoreEpisodes called, Then UiState should be end of list Success`() {
        // GIVEN
        val episodes = createEpisodesList(3)
        coEvery { getEpisodesUseCase(0) } returns GetEpisodesUseCase.Result.Success(episodes)
        coEvery { getEpisodesUseCase(1) } returns GetEpisodesUseCase.Result.EndOfList

        // WHEN
        viewModel.loadEpisodes()
        viewModel.loadMoreEpisodes()
        val actualUiState = viewModel.uiState.value
        val expectedUiState = EpisodesUiState.Success(episodes = episodes, isEndOfList = true)

        // THEN
        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `Given GetEpisodesUseCase returns no connection, When loadEpisodes called, Then UiState should be no connection error`() {
        // GIVEN
        coEvery { getEpisodesUseCase(0) } returns GetEpisodesUseCase.Result.NoInternetConnection

        // WHEN
        viewModel.loadEpisodes()
        val actualUiState = viewModel.uiState.value
        val expectedUiState = EpisodesUiState.Error.NoConnection

        // THEN
        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `Given GetEpisodesUseCase returns no connection, When loadMoreEpisodes called, Then UiState should be no connection Success`() {
        // GIVEN
        val episodes = createEpisodesList(3)
        coEvery { getEpisodesUseCase(0) } returns GetEpisodesUseCase.Result.Success(episodes)
        coEvery { getEpisodesUseCase(1) } returns GetEpisodesUseCase.Result.NoInternetConnection

        // WHEN
        viewModel.loadEpisodes()
        viewModel.loadMoreEpisodes()
        val actualUiState = viewModel.uiState.value
        val expectedUiState =
            EpisodesUiState.Success(episodes = episodes, isNoConnectionError = true)

        // THEN
        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `Given GetEpisodesUseCase returns unknown error, When loadEpisodes called, Then UiState should be unknown error`() {
        // GIVEN
        val errorMessage = "Unknown error"
        coEvery { getEpisodesUseCase(0) } returns GetEpisodesUseCase.Result.Failure(errorMessage)

        // WHEN
        viewModel.loadEpisodes()
        val actualUiState = viewModel.uiState.value
        val expectedUiState = EpisodesUiState.Error.Unknown(message = errorMessage)

        // THEN
        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `Given GetEpisodesUseCase returns unknown error, When loadMoreEpisodes called, Then UiState should be unknown error`() {
        // GIVEN
        val episodes = createEpisodesList(3)
        val errorMessage = "Unknown error"
        coEvery { getEpisodesUseCase(0) } returns GetEpisodesUseCase.Result.Success(episodes)
        coEvery { getEpisodesUseCase(1) } returns GetEpisodesUseCase.Result.Failure(errorMessage)

        // WHEN
        viewModel.loadEpisodes()
        viewModel.loadMoreEpisodes()
        val actualUiState = viewModel.uiState.value
        val expectedUiState = EpisodesUiState.Success(episodes = episodes, error = errorMessage)

        // THEN
        assertEquals(expectedUiState, actualUiState)
    }
}
