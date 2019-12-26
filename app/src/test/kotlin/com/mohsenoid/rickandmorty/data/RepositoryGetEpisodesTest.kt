package com.mohsenoid.rickandmorty.data

import com.mohsenoid.rickandmorty.data.db.dto.DbEpisodeModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkEpisodesResponse
import com.mohsenoid.rickandmorty.test.EpisodeDataFactory
import com.mohsenoid.rickandmorty.test.NetworkResponseFactory
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.When
import org.amshove.kluent.any
import org.amshove.kluent.calling
import org.amshove.kluent.itReturns
import org.junit.Test
import retrofit2.Response

class RepositoryGetEpisodesTest : RepositoryTest() {

    @Test
    fun `test if getEpisodes calls networkClient and db when isOnline`() {
        runBlocking {
            // GIVEN
            val page = 1
            stubConfigProviderIsOnline(true)
            stubNetworkClientFetchEpisodes(NetworkResponseFactory.Episode.episodesResponse())
            stubEpisodeDaoQueryAllEpisodesByPage(EpisodeDataFactory.Db.makeDbEpisodesModelList(5))

            // WHEN
            repository.getEpisodes(page)

            // THEN
            verify(episodeDao, times(1)).queryAllEpisodesByPage(page, RepositoryImpl.PAGE_SIZE)
            verify(networkClient, times(1)).fetchEpisodes(page)
        }
    }

    @Test
    fun `test if getEpisodes calls db only when isOffline`() {
        runBlocking {
            // GIVEN
            val page = 1
            stubConfigProviderIsOnline(false)
            stubEpisodeDaoQueryAllEpisodesByPage(EpisodeDataFactory.Db.makeDbEpisodesModelList(5))

            // WHEN
            repository.getEpisodes(page)

            // THEN
            verify(episodeDao, times(1)).queryAllEpisodesByPage(page, RepositoryImpl.PAGE_SIZE)
            verify(networkClient, times(0)).fetchEpisodes(page)
        }
    }

    private suspend fun stubNetworkClientFetchEpisodes(episodesResponse: Response<NetworkEpisodesResponse>) {
        When calling networkClient.fetchEpisodes(any()) itReturns episodesResponse
    }

    private suspend fun stubEpisodeDaoQueryAllEpisodesByPage(episodes: List<DbEpisodeModel>) {
        When calling episodeDao.queryAllEpisodesByPage(any(), any()) itReturns episodes
    }
}
