package com.mohsenoid.rickandmorty.data.episodes

import com.mohsenoid.rickandmorty.data.episodes.dao.EpisodesDao
import com.mohsenoid.rickandmorty.data.episodes.mapper.EpisodeMapper.toEpisode
import com.mohsenoid.rickandmorty.data.episodes.mapper.EpisodeMapper.toEpisodeEntity
import com.mohsenoid.rickandmorty.data.remote.ApiService
import com.mohsenoid.rickandmorty.data.remote.model.EpisodeRemoteModel
import com.mohsenoid.rickandmorty.domain.RepositoryGetResult
import com.mohsenoid.rickandmorty.domain.episodes.EpisodesRepository
import com.mohsenoid.rickandmorty.domain.episodes.model.Episode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class EpisodesRepositoryImpl(
    val apiService: ApiService,
    val episodesDao: EpisodesDao,
) : EpisodesRepository {

    private val episodesCache: MutableMap<Int, List<Episode>> = mutableMapOf()

    override suspend fun getEpisodes(page: Int): RepositoryGetResult<List<Episode>> =
        withContext(Dispatchers.IO) {
            val getCachedEpisodesResult = getCachedEpisodes(page)
            if (getCachedEpisodesResult is RepositoryGetResult.Success) {
                return@withContext getCachedEpisodesResult
            }

            val getDatabaseEpisodesResult = getDatabaseEpisodes(page)
            if (getDatabaseEpisodesResult is RepositoryGetResult.Success) {
                return@withContext getDatabaseEpisodesResult
            }

            return@withContext getRemoteEpisodes(page)
        }

    private fun getCachedEpisodes(page: Int): RepositoryGetResult<List<Episode>> {
        val cachedEpisodes = episodesCache.getOrDefault(page, emptyList())
        if (cachedEpisodes.isNotEmpty()) {
            return RepositoryGetResult.Success(cachedEpisodes)
        }

        return RepositoryGetResult.Failure.Unknown("No cached episodes")
    }

    private fun getDatabaseEpisodes(page: Int): RepositoryGetResult<List<Episode>> {
        val dbEpisodes = episodesDao.getEpisodes(page = page).map { it.toEpisode() }
        if (dbEpisodes.isNotEmpty()) {
            cacheEpisodes(page, dbEpisodes)
            return RepositoryGetResult.Success(dbEpisodes)
        }

        return RepositoryGetResult.Failure.Unknown("No database episodes")
    }

    private suspend fun getRemoteEpisodes(page: Int): RepositoryGetResult<List<Episode>> {
        val response = runCatching { apiService.getEpisodes(page) }.getOrNull()
            ?: return RepositoryGetResult.Failure.NoConnection("Connection Error!")
        val remoteEpisodes: List<EpisodeRemoteModel>? = response.body()?.results
        if (response.isSuccessful && remoteEpisodes != null) {
            val episodesEntity = remoteEpisodes.map { it.toEpisodeEntity(page) }
            episodesEntity.forEach { episodesDao.insertEpisode(it) }

            val serviceEpisodes = episodesEntity.map { it.toEpisode() }
            cacheEpisodes(page, serviceEpisodes)
            return RepositoryGetResult.Success(serviceEpisodes)
        } else if (response.code() == 404) {
            return RepositoryGetResult.Failure.EndOfList("End of list")
        } else {
            return RepositoryGetResult.Failure.Unknown(
                response.message().ifEmpty { "Unknown Error" },
            )
        }
    }

    private fun cacheEpisodes(page: Int, episodes: List<Episode>) {
        episodesCache[page] = episodes
    }
}
