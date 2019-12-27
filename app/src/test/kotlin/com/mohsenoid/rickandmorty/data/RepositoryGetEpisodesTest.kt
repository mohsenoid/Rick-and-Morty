package com.mohsenoid.rickandmorty.data

import com.mohsenoid.rickandmorty.data.db.dto.DbEpisodeModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkEpisodesResponse
import com.mohsenoid.rickandmorty.test.EpisodeDataFactory
import com.mohsenoid.rickandmorty.test.NetworkResponseFactory
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.Verify
import org.amshove.kluent.VerifyNoFurtherInteractions
import org.amshove.kluent.VerifyNoInteractions
import org.amshove.kluent.When
import org.amshove.kluent.any
import org.amshove.kluent.called
import org.amshove.kluent.calling
import org.amshove.kluent.itReturns
import org.amshove.kluent.on
import org.amshove.kluent.that
import org.amshove.kluent.was
import org.junit.Test
import retrofit2.Response

class RepositoryGetEpisodesTest : RepositoryTest() {

    @Test
    fun `test if getEpisodes calls networkClient and db when isOnline`() {
        runBlocking {
            // GIVEN
            stubConfigProviderIsOnline(isOnline = true)
            stubNetworkClientFetchEpisodes(NetworkResponseFactory.Episode.episodesResponse())
            stubEpisodeDaoQueryAllEpisodesByPage(EpisodeDataFactory.Db.makeEpisodes(count = 5))

            // WHEN
            repository.getEpisodes(page = 1)

            // THEN
            Verify on networkClient that networkClient.fetchEpisodes(page = any()) was called
            VerifyNoFurtherInteractions on networkClient

            Verify on episodeDao that episodeDao.insertEpisode(episode = any()) was called
            Verify on episodeDao that episodeDao.queryAllEpisodesByPage(
                page = any(),
                pageSize = any()
            ) was called
            VerifyNoFurtherInteractions on characterDao
        }
    }

    @Test
    fun `test if getEpisodes calls db only when isOffline`() {
        runBlocking {
            // GIVEN
            stubConfigProviderIsOnline(isOnline = false)
            stubEpisodeDaoQueryAllEpisodesByPage(EpisodeDataFactory.Db.makeEpisodes(count = 5))

            // WHEN
            repository.getEpisodes(page = 1)

            // THEN
            VerifyNoInteractions on networkClient

            Verify on episodeDao that episodeDao.queryAllEpisodesByPage(
                page = any(),
                pageSize = any()
            ) was called
            VerifyNoFurtherInteractions on characterDao
        }
    }

    private suspend fun stubNetworkClientFetchEpisodes(episodesResponse: Response<NetworkEpisodesResponse>) {
        When calling networkClient.fetchEpisodes(page = any()) itReturns episodesResponse
    }

    private suspend fun stubEpisodeDaoQueryAllEpisodesByPage(episodes: List<DbEpisodeModel>) {
        When calling episodeDao.queryAllEpisodesByPage(
            page = any(),
            pageSize = any()
        ) itReturns episodes
    }
}
