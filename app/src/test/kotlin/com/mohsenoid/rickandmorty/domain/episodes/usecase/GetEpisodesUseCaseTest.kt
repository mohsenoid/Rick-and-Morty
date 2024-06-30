package com.mohsenoid.rickandmorty.domain.episodes.usecase

import com.mohsenoid.rickandmorty.domain.EndOfListException
import com.mohsenoid.rickandmorty.domain.NoInternetConnectionException
import com.mohsenoid.rickandmorty.domain.episodes.EpisodeRepository
import com.mohsenoid.rickandmorty.util.createEpisodesList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetEpisodesUseCaseTest {
    private lateinit var episodeRepository: EpisodeRepository

    private lateinit var useCase: GetEpisodesUseCase

    @Before
    fun setUp() {
        episodeRepository = mockk()
        useCase = GetEpisodesUseCase(episodeRepository)
    }

    @Test
    fun `Given repository returns success, When use case is invoked, Then result is success`() =
        runTest {
            // GIVEN
            val expectedEpisodes = createEpisodesList(TEST_EPISODES_SIZE)
            coEvery { episodeRepository.getEpisodes(TEST_PAGE) } returns Result.success(expectedEpisodes)
            val expectedResult = GetEpisodesUseCase.Result.Success(expectedEpisodes)

            // WHEN
            val actualResult = useCase(TEST_PAGE)

            // THEN
            coVerify(exactly = 1) { episodeRepository.getEpisodes(TEST_PAGE) }
            assertEquals(expectedResult, actualResult)
        }

    @Test
    fun `Given repository returns failure caused by EndOfListException, When use case is invoked, Then result is EndOfList`() =
        runTest {
            // GIVEN
            coEvery { episodeRepository.getEpisodes(TEST_PAGE) } returns Result.failure(EndOfListException())
            val expectedResult = GetEpisodesUseCase.Result.EndOfList

            // WHEN
            val actualResult = useCase(TEST_PAGE)

            // THEN
            coVerify(exactly = 1) { episodeRepository.getEpisodes(TEST_PAGE) }
            assertEquals(expectedResult, actualResult)
        }

    @Test
    fun `Given repository returns failure caused by NoInternetConnectionException, When use case is invoked, Then result is NoInternetConnection`() =
        runTest {
            // GIVEN
            coEvery { episodeRepository.getEpisodes(TEST_PAGE) } returns Result.failure(NoInternetConnectionException("No Internet Connection"))
            val expectedResult = GetEpisodesUseCase.Result.NoInternetConnection

            // WHEN
            val actualResult = useCase(TEST_PAGE)

            // THEN
            coVerify(exactly = 1) { episodeRepository.getEpisodes(TEST_PAGE) }
            assertEquals(expectedResult, actualResult)
        }

    @Test
    fun `Given repository returns failure caused by unknown Exception, When use case is invoked, Then result is Failure`() =
        runTest {
            // GIVEN
            val errorMessage = "Unknown Error"
            coEvery { episodeRepository.getEpisodes(TEST_PAGE) } returns Result.failure(Exception(errorMessage))
            val expectedResult = GetEpisodesUseCase.Result.Failure(errorMessage)

            // WHEN
            val actualResult = useCase(TEST_PAGE)

            // THEN
            coVerify(exactly = 1) { episodeRepository.getEpisodes(TEST_PAGE) }
            assertEquals(expectedResult, actualResult)
        }

    companion object {
        private const val TEST_PAGE = 0
        private const val TEST_EPISODES_SIZE = 3
    }
}
