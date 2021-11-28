package com.mohsenoid.rickandmorty.data

import com.mohsenoid.rickandmorty.data.api.model.ApiEpisodes
import com.mohsenoid.rickandmorty.data.api.model.ApiResult
import com.mohsenoid.rickandmorty.data.db.model.DbEpisode
import com.mohsenoid.rickandmorty.test.ApiFactory
import com.mohsenoid.rickandmorty.test.EpisodeDataFactory
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryGetEpisodesTest : RepositoryTest() {

    @Test
    fun `getEpisodes calls networkClient and db when isOnline`() = runBlockingTest {
        // GIVEN
        stubStatusProviderIsOnline(isOnline = true)
        stubApiFetchEpisodes(ApiFactory.Episode.makeApiEpisodes())
        val entityEpisodes = EpisodeDataFactory.makeDbEpisodes(count = 5)
        stubEpisodeDaoQueryAllEpisodesByPage(entityEpisodes)
        stubDbInsertEpisode()

        // WHEN
        repository.getEpisodes(page = 1)

        // THEN
        coVerify(exactly = 1) { api.fetchEpisodes(page = 1) }
        coVerify(exactly = 1) { db.insertEpisode(entityEpisode = any()) }
        coVerify(exactly = 1) { db.queryAllEpisodesByPage(page = any(), pageSize = any()) }
    }

    @Test
    fun `if getEpisodes calls db only when isOffline`() = runBlockingTest {
        // GIVEN
        stubStatusProviderIsOnline(isOnline = false)
        stubEpisodeDaoQueryAllEpisodesByPage(EpisodeDataFactory.makeDbEpisodes(count = 5))

        // WHEN
        repository.getEpisodes(page = 1)

        // THEN
        coVerify(exactly = 0) { api.fetchEpisodes(page = 1) }
        coVerify(exactly = 1) { db.queryAllEpisodesByPage(page = any(), pageSize = any()) }
    }

    private fun stubApiFetchEpisodes(episodes: ApiEpisodes) {
        coEvery { api.fetchEpisodes(page = any()) } returns ApiResult.Success(episodes)
    }

    private fun stubDbInsertEpisode() {
        coEvery { db.insertEpisode(any()) } just runs
    }

    private fun stubEpisodeDaoQueryAllEpisodesByPage(entityEpisodes: List<DbEpisode>) {
        coEvery { db.queryAllEpisodesByPage(page = any(), pageSize = any()) } returns entityEpisodes
    }
}
