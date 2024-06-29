package com.mohsenoid.rickandmorty.data.episodes

import com.mohsenoid.rickandmorty.data.episodes.dao.EpisodeDao
import com.mohsenoid.rickandmorty.data.episodes.mapper.EpisodeMapper.toEpisode
import com.mohsenoid.rickandmorty.data.episodes.mapper.EpisodeMapper.toEpisodeEntity
import com.mohsenoid.rickandmorty.data.remote.ApiService
import com.mohsenoid.rickandmorty.data.remote.model.EpisodeRemoteModel
import com.mohsenoid.rickandmorty.domain.EndOfListException
import com.mohsenoid.rickandmorty.domain.NoInternetConnectionException
import com.mohsenoid.rickandmorty.domain.episodes.EpisodeRepository
import com.mohsenoid.rickandmorty.domain.episodes.model.Episode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import java.net.UnknownHostException

internal class EpisodeRepositoryImpl(
    private val apiService: ApiService,
    private val episodeDao: EpisodeDao,
) : EpisodeRepository {
    private val episodesCache: MutableMap<Int, List<Episode>> = mutableMapOf()

    override suspend fun getEpisodes(page: Int): Result<List<Episode>> =
        withContext(Dispatchers.IO) {
            getEpisodesFromCache(page)
                ?: getEpisodesFromDb(page)
                ?: getEpisodesFromRemote(page)
        }

    private fun getEpisodesFromCache(page: Int): Result<List<Episode>>? {
        return episodesCache[page]?.let { Result.success(it) }
    }

    private fun getEpisodesFromDb(page: Int): Result<List<Episode>>? {
        val dbEpisodes = episodeDao.getEpisodes(page).map { it.toEpisode() }
        return if (dbEpisodes.isNotEmpty()) {
            cacheEpisodes(page, dbEpisodes)
            Result.success(dbEpisodes)
        } else {
            null
        }
    }

    private suspend fun getEpisodesFromRemote(page: Int): Result<List<Episode>> {
        return try {
            val response = apiService.getEpisodes(page)
            val remoteEpisodes = response.body()?.results
            if (response.isSuccessful && remoteEpisodes != null) {
                handleSuccessfulRemoteResponse(page, remoteEpisodes)
                getEpisodesFromCache(page)!! // All episodes should be cached
            } else if (response.code() == HTTP_CODE_404 && page != 0) {
                Result.failure(EndOfListException())
            } else {
                Result.failure(Exception(response.message().ifEmpty { "Unknown Error" }))
            }
        } catch (e: UnknownHostException) {
            Result.failure(NoInternetConnectionException(e.message))
        } catch (e: SocketTimeoutException) {
            Result.failure(NoInternetConnectionException(e.message))
        }
    }

    private fun handleSuccessfulRemoteResponse(
        page: Int,
        remoteEpisodes: List<EpisodeRemoteModel>,
    ) {
        val episodesEntity = remoteEpisodes.map { it.toEpisodeEntity(page) }
        episodesEntity.forEach { episodeDao.insertEpisode(it) }
        val episodes = episodesEntity.map { it.toEpisode() }
        cacheEpisodes(page, episodes)
    }

    private fun cacheEpisodes(
        page: Int,
        episodes: List<Episode>,
    ) {
        episodesCache[page] = episodes
    }

    companion object {
        private const val HTTP_CODE_404 = 404
    }
}
