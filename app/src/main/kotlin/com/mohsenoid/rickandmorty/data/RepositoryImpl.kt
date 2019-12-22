package com.mohsenoid.rickandmorty.data

import com.mohsenoid.rickandmorty.data.db.Db
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
import com.mohsenoid.rickandmorty.util.executor.TaskExecutor

class RepositoryImpl(
    private val db: Db,
    private val networkClient: NetworkClient,
    private val ioTaskExecutor: TaskExecutor,
    private val mainTaskExecutor: TaskExecutor,
    private val configProvider: ConfigProvider,
    private val episodeDbMapper: Mapper<NetworkEpisodeModel, DbEpisodeModel>,
    private val episodeEntityMapper: Mapper<DbEpisodeModel, EpisodeEntity>,
    private val characterDbMapper: Mapper<NetworkCharacterModel, DbCharacterModel>,
    private val characterEntityMapper: Mapper<DbCharacterModel, CharacterEntity>
) : Repository {

    override fun queryEpisodes(
        page: Int,
        callback: DataCallback<List<EpisodeEntity>>?
    ) {
        if (configProvider.isOnline()) {
            queryNetworkEpisodes(page, callback)
        } else {
            queryDbEpisodes(page, callback)
        }
    }

    private fun queryNetworkEpisodes(
        page: Int,
        callback: DataCallback<List<EpisodeEntity>>?
    ) {
        ioTaskExecutor.execute {
            try {
                val networkEpisodes = networkClient.getEpisodes(page)

                networkEpisodes
                    .map(episodeDbMapper::map)
                    .forEach { db.insertEpisode(it) }

                queryDbEpisodes(page, callback)
            } catch (e: Exception) {
                e.printStackTrace()
                queryDbEpisodes(page, callback)
            }
        }
    }

    private fun queryDbEpisodes(
        page: Int,
        callback: DataCallback<List<EpisodeEntity>>?
    ) {
        ioTaskExecutor.execute {
            try {
                val dbEpisodes = db.queryAllEpisodes(page)

                val episodes = dbEpisodes.map(episodeEntityMapper::map)

                mainTaskExecutor.execute {
                    if (episodes.isNotEmpty()) {
                        callback?.onSuccess(episodes)
                    } else {
                        callback?.onError(EndOfListException())
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                mainTaskExecutor.execute { callback?.onError(e) }
            }
        }
    }

    override fun queryCharactersByIds(
        characterIds: List<Int>,
        callback: DataCallback<List<CharacterEntity>>?
    ) {
        if (configProvider.isOnline()) {
            queryNetworkCharactersByIds(characterIds, callback)
        } else {
            queryDbCharactersByIds(characterIds, callback)
        }
    }

    private fun queryNetworkCharactersByIds(
        characterIds: List<Int>,
        callback: DataCallback<List<CharacterEntity>>?
    ) {
        ioTaskExecutor.execute {
            try {
                val networkCharacters = networkClient.getCharactersByIds(characterIds)

                networkCharacters
                    .map(characterDbMapper::map)
                    .forEach { db.insertCharacter(it) }

                queryDbCharactersByIds(characterIds, callback)
            } catch (e: Exception) {
                e.printStackTrace()
                queryDbCharactersByIds(characterIds, callback)
            }
        }
    }

    private fun queryDbCharactersByIds(
        characterIds: List<Int>,
        callback: DataCallback<List<CharacterEntity>>?
    ) {
        ioTaskExecutor.execute {
            try {
                val dbCharacters = db.queryCharactersByIds(characterIds)

                val characters = dbCharacters.map(characterEntityMapper::map)

                mainTaskExecutor.execute {
                    if (characters.isNotEmpty()) {
                        callback?.onSuccess(characters)
                    } else {
                        callback?.onError(NoOfflineDataException())
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                mainTaskExecutor.execute { callback?.onError(e) }
            }
        }
    }

    override fun queryCharacterDetails(
        characterId: Int,
        callback: DataCallback<CharacterEntity>?
    ) {
        if (configProvider.isOnline()) {
            queryNetworkCharacterDetails(characterId, callback)
        } else {
            queryDbCharacterDetails(characterId, callback)
        }
    }

    private fun queryNetworkCharacterDetails(
        characterId: Int,
        callback: DataCallback<CharacterEntity>?
    ) {
        ioTaskExecutor.execute {
            try {
                val networkCharacter = networkClient.getCharacterDetails(characterId)

                val dbCharacterModel = characterDbMapper.map(networkCharacter)

                db.insertCharacter(dbCharacterModel)
                queryDbCharacterDetails(characterId, callback)
            } catch (e: Exception) {
                e.printStackTrace()
                queryDbCharacterDetails(characterId, callback)
            }
        }
    }

    private fun queryDbCharacterDetails(
        characterId: Int,
        callback: DataCallback<CharacterEntity>?
    ) {
        ioTaskExecutor.execute {
            try {
                val dbCharacter = db.queryCharacter(characterId)
                if (dbCharacter != null) {
                    val character = characterEntityMapper.map(dbCharacter)
                    mainTaskExecutor.execute {
                        callback?.onSuccess(character)
                    }
                } else {
                    mainTaskExecutor.execute {
                        callback?.onError(NoOfflineDataException())
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                mainTaskExecutor.execute { callback?.onError(e) }
            }
        }
    }

    override fun killCharacter(
        characterId: Int,
        callback: DataCallback<CharacterEntity>?
    ) {
        ioTaskExecutor.execute {
            try {
                db.killCharacter(characterId)
                queryCharacterDetails(characterId, callback)
            } catch (e: Exception) {
                e.printStackTrace()
                mainTaskExecutor.execute { callback?.onError(e) }
            }
        }
    }
}
