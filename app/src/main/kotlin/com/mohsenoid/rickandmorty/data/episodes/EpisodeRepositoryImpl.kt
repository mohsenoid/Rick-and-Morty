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

internal class EpisodeRepositoryImpl(
    val apiService: ApiService,
    val episodeDao: EpisodeDao,
) : EpisodeRepository {
    private val episodesCache: MutableMap<Int, List<Episode>> = mutableMapOf()

    override suspend fun getEpisodes(page: Int): Result<List<Episode>> =
        withContext(Dispatchers.IO) {
            val getCachedEpisodesResult = getCachedEpisodes(page)
            if (getCachedEpisodesResult.isSuccess) {
                return@withContext getCachedEpisodesResult
            }

            val getDatabaseEpisodesResult = getDatabaseEpisodes(page)
            if (getDatabaseEpisodesResult.isSuccess) {
                return@withContext getDatabaseEpisodesResult
            }

            return@withContext getRemoteEpisodes(page)
        }

    private fun getCachedEpisodes(page: Int): Result<List<Episode>> {
        val cachedEpisodes = episodesCache.getOrDefault(page, emptyList())
        if (cachedEpisodes.isNotEmpty()) {
            return Result.success(cachedEpisodes)
        }

        return Result.failure(Exception("No cached episodes"))
    }

    private fun getDatabaseEpisodes(page: Int): Result<List<Episode>> {
        val dbEpisodes = episodeDao.getEpisodes(page = page).map { it.toEpisode() }
        if (dbEpisodes.isNotEmpty()) {
            cacheEpisodes(page, dbEpisodes)
            return Result.success(dbEpisodes)
        }

        return Result.failure(Exception("No database episodes"))
    }

    private suspend fun getRemoteEpisodes(page: Int): Result<List<Episode>> {
        val response =
            try {
                apiService.getEpisodes(page)
            } catch (e: Exception) {
                return Result.failure(NoInternetConnectionException())
            }
        val remoteEpisodes: List<EpisodeRemoteModel>? = response.body()?.results
        return if (response.isSuccessful && remoteEpisodes != null) {
            val episodesEntity = remoteEpisodes.map { it.toEpisodeEntity(page) }
            episodesEntity.forEach { episodeDao.insertEpisode(it) }

            val serviceEpisodes = episodesEntity.map { it.toEpisode() }
            cacheEpisodes(page, serviceEpisodes)
            Result.success(serviceEpisodes)
        } else if (response.code() == HTTP_CODE_404 && page != 0) {
            Result.failure(EndOfListException())
        } else {
            Result.failure(Exception(response.message().ifEmpty { "Unknown Error" }))
        }
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
