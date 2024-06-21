package com.mohsenoid.rickandmorty.ui.episodes

import com.mohsenoid.rickandmorty.domain.RepositoryGetResult
import com.mohsenoid.rickandmorty.domain.episodes.EpisodesRepository
import com.mohsenoid.rickandmorty.util.MainDispatcherRule
import com.mohsenoid.rickandmorty.util.createEpisodesList
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class EpisodesViewModelTest {
    @get:Rule
    var rule: TestRule = MainDispatcherRule()

    private lateinit var repository: EpisodesRepository

    private lateinit var viewModel: EpisodesViewModel

    @Before
    fun setUp() {
        repository = mockk()
        viewModel = EpisodesViewModel(episodesRepository = repository)
    }

    @Test
    fun `When initialized, Then UiState initial value should be Loading`() {
        // GIVEN

        // WHEN
        val actualUiState = viewModel.uiState.value
        val expectedUiState = EpisodesUiState(isLoading = true)

        // THEN
        assertEquals(expectedUiState, actualUiState)
    }

    @Test
    fun `Given repository returns Success, When loadEpisodes called, Then UiState should be Success`() {
        // GIVEN
        val episodes = createEpisodesList(3)
        coEvery { repository.getEpisodes(0) } returns RepositoryGetResult.Success(episodes)

        // WHEN
        viewModel.loadEpisodes()
        val actualUiState = viewModel.uiState.value
        val expectedUiState = EpisodesUiState(episodes = episodes)

        // THEN
        assertEquals(expectedUiState, actualUiState)
    }

//    @Test
//    fun `Given repository returns end of list, When loadMoreEpisodes called, Then UiState should be end of list Success`() {
//        // GIVEN
//        val episodes = createEpisodesList(3)
//        coEvery { repository.getEpisodes(0) } returns RepositoryGetResult.Success(episodes)
//        coEvery { repository.getEpisodes(1) } returns RepositoryGetResult.Failure.EndOfList("End of list")
//
//        // WHEN
//        viewModel.loadEpisodes()
//        viewModel.loadMoreEpisodes()
//        val actualUiState = viewModel.uiState.value
//        val expectedUiState = EpisodesUiState(episodes = episodes, isEndOfList = true)
//
//        // THEN
//        assertEquals(expectedUiState, actualUiState)
//    }
}
