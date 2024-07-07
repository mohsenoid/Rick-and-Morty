package com.mohsenoid.rickandmorty.data.episodes

import com.mohsenoid.rickandmorty.data.episodes.db.dao.EpisodeDao
import com.mohsenoid.rickandmorty.data.episodes.db.entity.EpisodeEntity
import com.mohsenoid.rickandmorty.data.episodes.mapper.EpisodeMapper.toEpisode
import com.mohsenoid.rickandmorty.data.episodes.mapper.EpisodeMapper.toEpisodeEntity
import com.mohsenoid.rickandmorty.data.episodes.remote.EpisodeApiService
import com.mohsenoid.rickandmorty.data.episodes.remote.model.EpisodeRemoteModel
import com.mohsenoid.rickandmorty.domain.episodes.EpisodeRepository
import com.mohsenoid.rickandmorty.domain.episodes.model.Episode
import com.mohsenoid.rickandmorty.util.createEpisodeResponse
import com.mohsenoid.rickandmorty.util.createEpisodesEntityList
import com.mohsenoid.rickandmorty.util.createEpisodesRemoteModelList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Before
import retrofit2.Response
import kotlin.test.Test
import kotlin.test.assertContentEquals

class EpisodeRepositoryImplTest {
    private lateinit var episodeApiService: EpisodeApiService
    private lateinit var episodeDao: EpisodeDao

    private lateinit var repository: EpisodeRepository

    @Before
    fun setUp() {
        episodeApiService = mockk()
        episodeDao = mockk()

        repository =
            EpisodeRepositoryImpl(
                episodeApiService = episodeApiService,
                episodeDao = episodeDao,
            )
    }

    @Test
    fun `Given db has episodes, When getEpisodes, Then return db episodes`() =
        runTest {
            // GIVEN
            val episodeEntities: List<EpisodeEntity> = createEpisodesEntityList(TEST_EPISODES_SIZE)
            val expectedEpisodes: List<Episode> = episodeEntities.map { it.toEpisode() }
            coEvery { episodeDao.getEpisodes(TEST_PAGE) } returns episodeEntities

            // WHEN
            val actualEpisodes: List<Episode>? = repository.getEpisodes(TEST_PAGE).getOrNull()

            // THEN
            assertContentEquals(expectedEpisodes, actualEpisodes)
            coVerify(exactly = 1) { episodeDao.getEpisodes(any()) }
            coVerify(exactly = 0) { episodeApiService.getEpisodes(any()) }
        }

    @Test
    fun `Given db has episodes, When the second time getEpisodes, Then return cached episodes`() =
        runTest {
            // GIVEN
            val episodeEntities: List<EpisodeEntity> = createEpisodesEntityList(TEST_EPISODES_SIZE)
            val expectedEpisodes: List<Episode> = episodeEntities.map { it.toEpisode() }
            coEvery { episodeDao.getEpisodes(TEST_PAGE) } returns episodeEntities

            // WHEN
            repository.getEpisodes(TEST_PAGE).getOrNull()

            // THEN
            coVerify(exactly = 1) { episodeDao.getEpisodes(any()) }
            coVerify(exactly = 0) { episodeApiService.getEpisodes(any()) }

            // WHEN
            val actualEpisodes: List<Episode>? = repository.getEpisodes(TEST_PAGE).getOrNull()

            // THEN
            assertContentEquals(expectedEpisodes, actualEpisodes)
        }

    @Test
    fun `Given db has no episodes, When getEpisodes, Then return remote episodes`() =
        runTest {
            // GIVEN
            val episodesRemoteModel: List<EpisodeRemoteModel> =
                createEpisodesRemoteModelList(TEST_EPISODES_SIZE)
            val episodeEntities: List<EpisodeEntity> =
                episodesRemoteModel.map { it.toEpisodeEntity(TEST_PAGE) }
            val expectedEpisodes: List<Episode> = episodeEntities.map { it.toEpisode() }
            val episodeResponse = createEpisodeResponse(results = episodesRemoteModel)
            coEvery { episodeDao.getEpisodes(TEST_PAGE) } returns emptyList()
            episodeEntities.forEach { episodeEntity ->
                coEvery { episodeDao.insertEpisode(episodeEntity) } just runs
            }
            coEvery { episodeApiService.getEpisodes(TEST_PAGE) } returns Response.success(episodeResponse)

            // WHEN
            val actualEpisodes: List<Episode>? = repository.getEpisodes(TEST_PAGE).getOrNull()

            // THEN
            assertContentEquals(expectedEpisodes, actualEpisodes)
            coVerify(exactly = 1) { episodeDao.getEpisodes(any()) }
            coVerify(exactly = 1) { episodeApiService.getEpisodes(any()) }
            coVerify(exactly = TEST_EPISODES_SIZE) { episodeDao.insertEpisode(any()) }
        }

    companion object {
        private const val TEST_PAGE = 0
        private const val TEST_EPISODES_SIZE = 3
    }
}
