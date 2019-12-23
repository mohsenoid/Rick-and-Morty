package com.mohsenoid.rickandmorty.data

import com.mohsenoid.rickandmorty.data.db.DbCharacterDao
import com.mohsenoid.rickandmorty.data.db.DbEpisodeDao
import com.mohsenoid.rickandmorty.data.db.dto.DbCharacterModel
import com.mohsenoid.rickandmorty.data.db.dto.DbEpisodeModel
import com.mohsenoid.rickandmorty.data.exception.EndOfListException
import com.mohsenoid.rickandmorty.data.exception.NoOfflineDataException
import com.mohsenoid.rickandmorty.data.mapper.Mapper
import com.mohsenoid.rickandmorty.data.network.NetworkClient
import com.mohsenoid.rickandmorty.data.network.dto.NetworkCharacterModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkEpisodeModel
import com.mohsenoid.rickandmorty.domain.Repository
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity
import com.mohsenoid.rickandmorty.domain.entity.EpisodeEntity
import com.mohsenoid.rickandmorty.util.config.ConfigProvider
import com.mohsenoid.rickandmorty.util.dispatcher.DispatcherProvider
import kotlinx.coroutines.withContext

class RepositoryImpl(
    private val characterDao: DbCharacterDao,
    private val episodeDao: DbEpisodeDao,
    private val networkClient: NetworkClient,
    private val dispatcherProvider: DispatcherProvider,
    private val configProvider: ConfigProvider,
    private val episodeDbMapper: Mapper<NetworkEpisodeModel, DbEpisodeModel>,
    private val episodeEntityMapper: Mapper<DbEpisodeModel, EpisodeEntity>,
    private val characterDbMapper: Mapper<NetworkCharacterModel, DbCharacterModel>,
    private val characterEntityMapper: Mapper<DbCharacterModel, CharacterEntity>
) : Repository {

    override suspend fun queryEpisodes(
        page: Int,
        callback: DataCallback<List<EpisodeEntity>>?
    ) {
        if (configProvider.isOnline()) {
            queryNetworkEpisodes(page, callback)
        } else {
            queryDbEpisodes(page, callback)
        }
    }

    private suspend fun queryNetworkEpisodes(
        page: Int,
        callback: DataCallback<List<EpisodeEntity>>?
    ) {
        withContext(dispatcherProvider.ioDispatcher) {
            try {
                val networkEpisodesResponse = networkClient.getEpisodes(page)

                if (networkEpisodesResponse.isSuccessful) {
                    networkEpisodesResponse.body()?.let { networkEpisodes ->
                        networkEpisodes.results
                            .map(episodeDbMapper::map)
                            .forEach { episodeDao.insertEpisode(it) }
                    }
                }

                queryDbEpisodes(page, callback)
            } catch (e: Exception) {
                e.printStackTrace()
                queryDbEpisodes(page, callback)
            }
        }
    }

    private suspend fun queryDbEpisodes(
        page: Int,
        callback: DataCallback<List<EpisodeEntity>>?
    ) {
        withContext(dispatcherProvider.ioDispatcher) {
            try {
                val dbEpisodes = episodeDao.queryAllEpisodes(page)

                val episodes = dbEpisodes.map(episodeEntityMapper::map)

                withContext(dispatcherProvider.mainDispatcher) {
                    if (episodes.isNotEmpty()) {
                        callback?.onSuccess(episodes)
                    } else {
                        callback?.onError(EndOfListException())
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(dispatcherProvider.mainDispatcher) { callback?.onError(e) }
            }
        }
    }

    override suspend fun queryCharactersByIds(
        characterIds: List<Int>,
        callback: DataCallback<List<CharacterEntity>>?
    ) {
        if (configProvider.isOnline()) {
            queryNetworkCharactersByIds(characterIds, callback)
        } else {
            queryDbCharactersByIds(characterIds, callback)
        }
    }

    private suspend fun queryNetworkCharactersByIds(
        characterIds: List<Int>,
        callback: DataCallback<List<CharacterEntity>>?
    ) {
        withContext(dispatcherProvider.ioDispatcher) {
            try {
                val networkCharactersResponse = networkClient.getCharactersByIds(characterIds)

                if (networkCharactersResponse.isSuccessful) {
                    networkCharactersResponse.body()?.let { networkCharacters ->
                        networkCharacters
                            .map(characterDbMapper::map)
                            .forEach { characterDao.insertOrUpdateCharacter(it) }
                    }
                }

                queryDbCharactersByIds(characterIds, callback)
            } catch (e: Exception) {
                e.printStackTrace()
                queryDbCharactersByIds(characterIds, callback)
            }
        }
    }

    private suspend fun queryDbCharactersByIds(
        characterIds: List<Int>,
        callback: DataCallback<List<CharacterEntity>>?
    ) {
        withContext(dispatcherProvider.ioDispatcher) {
            try {
                val dbCharacters = characterDao.queryCharactersByIds(characterIds)

                val characters = dbCharacters.map(characterEntityMapper::map)

                withContext(dispatcherProvider.mainDispatcher) {
                    if (characters.isNotEmpty()) {
                        callback?.onSuccess(characters)
                    } else {
                        callback?.onError(NoOfflineDataException())
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(dispatcherProvider.mainDispatcher) { callback?.onError(e) }
            }
        }
    }

    override suspend fun queryCharacterDetails(
        characterId: Int,
        callback: DataCallback<CharacterEntity>?
    ) {
        if (configProvider.isOnline()) {
            queryNetworkCharacterDetails(characterId, callback)
        } else {
            queryDbCharacterDetails(characterId, callback)
        }
    }

    private suspend fun queryNetworkCharacterDetails(
        characterId: Int,
        callback: DataCallback<CharacterEntity>?
    ) {
        withContext(dispatcherProvider.ioDispatcher) {
            try {
                val networkCharacterResponse = networkClient.getCharacterDetails(characterId)

                if (networkCharacterResponse.isSuccessful) {
                    networkCharacterResponse.body()?.let { networkCharacter ->
                        val dbCharacterModel = characterDbMapper.map(networkCharacter)
                        characterDao.insertOrUpdateCharacter(dbCharacterModel)
                    }
                }

                queryDbCharacterDetails(characterId, callback)
            } catch (e: Exception) {
                e.printStackTrace()
                queryDbCharacterDetails(characterId, callback)
            }
        }
    }

    private suspend fun queryDbCharacterDetails(
        characterId: Int,
        callback: DataCallback<CharacterEntity>?
    ) {
        withContext(dispatcherProvider.ioDispatcher) {
            try {
                val dbCharacter = characterDao.queryCharacter(characterId)
                if (dbCharacter != null) {
                    val character = characterEntityMapper.map(dbCharacter)
                    withContext(dispatcherProvider.mainDispatcher) {
                        callback?.onSuccess(character)
                    }
                } else {
                    withContext(dispatcherProvider.mainDispatcher) {
                        callback?.onError(NoOfflineDataException())
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(dispatcherProvider.mainDispatcher) { callback?.onError(e) }
            }
        }
    }

    override suspend fun killCharacter(
        characterId: Int,
        callback: DataCallback<CharacterEntity>?
    ) {
        withContext(dispatcherProvider.ioDispatcher) {
            try {
                characterDao.killCharacter(characterId)
                queryCharacterDetails(characterId, callback)
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(dispatcherProvider.mainDispatcher) { callback?.onError(e) }
            }
        }
    }
}
