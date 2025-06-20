package com.mohsenoid.rickandmorty.data.episodes

import com.mohsenoid.rickandmorty.data.episodes.db.dao.EpisodeDao
import com.mohsenoid.rickandmorty.data.episodes.mapper.EpisodeMapper.toEpisode
import com.mohsenoid.rickandmorty.data.episodes.mapper.EpisodeMapper.toEpisodeEntity
import com.mohsenoid.rickandmorty.data.episodes.remote.EpisodeApiService
import com.mohsenoid.rickandmorty.data.episodes.remote.model.EpisodeRemoteModel
import com.mohsenoid.rickandmorty.domain.EndOfListException
import com.mohsenoid.rickandmorty.domain.NoInternetConnectionException
import com.mohsenoid.rickandmorty.domain.episodes.EpisodeRepository
import com.mohsenoid.rickandmorty.domain.episodes.model.Episode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import java.net.HttpURLConnection

internal class EpisodeRepositoryImpl(
    private val episodeApiService: EpisodeApiService,
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

    private suspend fun getEpisodesFromDb(page: Int): Result<List<Episode>>? {
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
            val response = episodeApiService.getEpisodes(page)
            val remoteEpisodes = response.body()?.results
            if (response.isSuccessful && remoteEpisodes != null) {
                handleSuccessfulRemoteResponse(page, remoteEpisodes)
                getEpisodesFromCache(page)!! // All episodes should be cached
            } else if (response.code() == HttpURLConnection.HTTP_NOT_FOUND && page != 0) {
                Result.failure(EndOfListException())
            } else {
                Result.failure(Exception(response.message().ifEmpty { "Unknown Error" }))
            }
        } catch (e: IOException) {
            Result.failure(NoInternetConnectionException(e.message))
        } catch (e:Exception){
            Result.failure(e)
        }
    }

    private suspend fun handleSuccessfulRemoteResponse(
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
}
