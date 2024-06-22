package com.mohsenoid.rickandmorty.data.episodes

import com.mohsenoid.rickandmorty.data.episodes.dao.EpisodeDao
import com.mohsenoid.rickandmorty.data.episodes.entity.EpisodeEntity
import com.mohsenoid.rickandmorty.data.episodes.mapper.EpisodeMapper.toEpisode
import com.mohsenoid.rickandmorty.data.episodes.mapper.EpisodeMapper.toEpisodeEntity
import com.mohsenoid.rickandmorty.data.remote.ApiService
import com.mohsenoid.rickandmorty.data.remote.model.EpisodeRemoteModel
import com.mohsenoid.rickandmorty.domain.episodes.EpisodesRepository
import com.mohsenoid.rickandmorty.domain.episodes.model.Episode
import com.mohsenoid.rickandmorty.util.createEpisodeResponse
import com.mohsenoid.rickandmorty.util.createEpisodesEntityList
import com.mohsenoid.rickandmorty.util.createEpisodesRemoteModelList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import kotlin.test.assertContentEquals

class EpisodesRepositoryImplTest {
    private lateinit var apiService: ApiService
    private lateinit var episodeDao: EpisodeDao

    private lateinit var repository: EpisodesRepository

    @Before
    fun setUp() {
        apiService = mockk()
        episodeDao = mockk()

        repository =
            EpisodesRepositoryImpl(
                apiService = apiService,
                episodeDao = episodeDao,
            )
    }

    @Test
    fun `Given db has episodes, When getEpisodes, Then return db episodes`() =
        runTest {
            // GIVEN
            val episodeEntities: List<EpisodeEntity> = createEpisodesEntityList(3)
            val expectedEpisodes: List<Episode> = episodeEntities.map { it.toEpisode() }
            every { episodeDao.getEpisodes(TEST_PAGE) } returns episodeEntities

            // WHEN
            val actualEpisodes: List<Episode>? = repository.getEpisodes(TEST_PAGE).getOrNull()

            // THEN
            assertContentEquals(expectedEpisodes, actualEpisodes)
            verify(exactly = 1) { episodeDao.getEpisodes(TEST_PAGE) }
            coVerify(exactly = 0) { apiService.getEpisodes(TEST_PAGE) }
        }

    @Test
    fun `Given db has episodes, When the second time getEpisodes, Then return cached episodes`() =
        runTest {
            // GIVEN
            val episodeEntities: List<EpisodeEntity> = createEpisodesEntityList(3)
            val expectedEpisodes: List<Episode> = episodeEntities.map { it.toEpisode() }
            every { episodeDao.getEpisodes(TEST_PAGE) } returns episodeEntities

            // WHEN
            repository.getEpisodes(TEST_PAGE).getOrNull()

            // THEN
            verify(exactly = 1) { episodeDao.getEpisodes(TEST_PAGE) }
            coVerify(exactly = 0) { apiService.getEpisodes(TEST_PAGE) }

            // WHEN
            val actualEpisodes: List<Episode>? = repository.getEpisodes(TEST_PAGE).getOrNull()

            // THEN
            assertContentEquals(expectedEpisodes, actualEpisodes)
        }

    @Test
    fun `Given db has no episodes, When getEpisodes, Then return remote episodes`() =
        runTest {
            // GIVEN
            val episodesRemoteModel: List<EpisodeRemoteModel> = createEpisodesRemoteModelList(3)
            val episodeEntities: List<EpisodeEntity> =
                episodesRemoteModel.map { it.toEpisodeEntity(TEST_PAGE) }
            val expectedEpisodes: List<Episode> = episodeEntities.map { it.toEpisode() }

            val episodeResponse = createEpisodeResponse(results = episodesRemoteModel)
            every { episodeDao.getEpisodes(TEST_PAGE) } returns emptyList()
            every { episodeDao.insertEpisode(any()) } just runs
            coEvery {
                apiService.getEpisodes(TEST_PAGE)
            } returns Response.success(episodeResponse)

            // WHEN
            val actualEpisodes: List<Episode>? = repository.getEpisodes(TEST_PAGE).getOrNull()

            // THEN
            assertContentEquals(expectedEpisodes, actualEpisodes)
            verify(exactly = 1) { episodeDao.getEpisodes(TEST_PAGE) }
            coVerify(exactly = 1) { apiService.getEpisodes(TEST_PAGE) }
            coVerify(exactly = 3) { episodeDao.insertEpisode(any()) }
        }

    companion object {
        private const val TEST_PAGE = 0
    }
}
