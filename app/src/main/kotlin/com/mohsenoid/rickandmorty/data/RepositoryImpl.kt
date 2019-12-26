package com.mohsenoid.rickandmorty.data

import com.mohsenoid.rickandmorty.data.db.DbCharacterDao
import com.mohsenoid.rickandmorty.data.db.DbEpisodeDao
import com.mohsenoid.rickandmorty.data.db.dto.DbCharacterModel
import com.mohsenoid.rickandmorty.data.db.dto.DbEpisodeModel
import com.mohsenoid.rickandmorty.data.exception.EndOfListException
import com.mohsenoid.rickandmorty.data.exception.NoOfflineDataException
import com.mohsenoid.rickandmorty.data.exception.ServerException
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

    override suspend fun getEpisodes(page: Int): List<EpisodeEntity> {
        return withContext(dispatcherProvider.ioDispatcher) {
            if (configProvider.isOnline()) {
                try {
                    val episodes = fetchNetworkEpisodes(page)
                    cacheNetworkEpisodes(episodes)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            queryDbEpisodes(page, PAGE_SIZE)
        }
    }

    private suspend fun fetchNetworkEpisodes(page: Int): List<NetworkEpisodeModel> {
        val networkEpisodesResponse = networkClient.fetchEpisodes(page)

        if (networkEpisodesResponse.isSuccessful) {
            networkEpisodesResponse.body()?.let { networkEpisodes ->
                return networkEpisodes.results
            } ?: throw ServerException(networkEpisodesResponse.code(), "Response body is empty!")
        } else {
            throw ServerException(
                networkEpisodesResponse.code(),
                networkEpisodesResponse.errorBody().toString()
            )
        }
    }

    private suspend fun cacheNetworkEpisodes(episodes: List<NetworkEpisodeModel>) {
        episodes.map(episodeDbMapper::map)
            .forEach { episodeDao.insertEpisode(it) }
    }

    private suspend fun queryDbEpisodes(page: Int, pageSize: Int): List<EpisodeEntity> {
        val dbEpisodes = episodeDao.queryAllEpisodesByPage(page, pageSize)
        val episodes = dbEpisodes.map(episodeEntityMapper::map)

        if (episodes.isEmpty()) throw EndOfListException()

        return episodes
    }

    override suspend fun getCharactersByIds(characterIds: List<Int>): List<CharacterEntity> {
        return withContext(dispatcherProvider.ioDispatcher) {
            if (configProvider.isOnline()) {
                try {
                    val characters = fetchNetworkCharactersByIds(characterIds)
                    cacheNetworkCharacters(characters)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            queryDbCharactersByIds(characterIds)
        }
    }

    private suspend fun fetchNetworkCharactersByIds(characterIds: List<Int>): List<NetworkCharacterModel> {
        val networkCharactersResponse = networkClient.fetchCharactersByIds(characterIds)

        if (networkCharactersResponse.isSuccessful) {
            networkCharactersResponse.body()?.let { networkCharacters ->
                return networkCharacters
            } ?: throw ServerException(networkCharactersResponse.code(), "Response body is empty!")
        } else {
            throw ServerException(
                networkCharactersResponse.code(),
                networkCharactersResponse.errorBody().toString()
            )
        }
    }

    private suspend fun cacheNetworkCharacters(characters: List<NetworkCharacterModel>) {
        characters.map(characterDbMapper::map)
            .forEach { characterDao.insertOrUpdateCharacter(it) }
    }

    private suspend fun queryDbCharactersByIds(characterIds: List<Int>): List<CharacterEntity> {
        return withContext(dispatcherProvider.ioDispatcher) {
            val dbCharacters = characterDao.queryCharactersByIds(characterIds)

            val characters = dbCharacters.map(characterEntityMapper::map)

            if (characters.isEmpty()) throw NoOfflineDataException()

            characters
        }
    }

    override suspend fun getCharacterDetails(characterId: Int): CharacterEntity {
        return withContext(dispatcherProvider.ioDispatcher) {
            if (configProvider.isOnline()) {
                try {
                    val character = fetchNetworkCharacterDetails(characterId)
                    cacheNetworkCharacter(character)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            queryDbCharacterDetails(characterId)
        }
    }

    private suspend fun fetchNetworkCharacterDetails(characterId: Int): NetworkCharacterModel {
        val networkCharacterResponse = networkClient.fetchCharacterDetails(characterId)

        if (networkCharacterResponse.isSuccessful) {
            networkCharacterResponse.body()?.let { networkCharacter ->
                return networkCharacter
            } ?: throw ServerException(networkCharacterResponse.code(), "Response body is empty!")
        } else {
            throw ServerException(
                networkCharacterResponse.code(),
                networkCharacterResponse.errorBody().toString()
            )
        }
    }

    private suspend fun cacheNetworkCharacter(character: NetworkCharacterModel) {
        characterDao.insertOrUpdateCharacter(characterDbMapper.map(character))
    }

    private suspend fun queryDbCharacterDetails(characterId: Int): CharacterEntity {
        return withContext(dispatcherProvider.ioDispatcher) {
            val dbCharacter =
                characterDao.queryCharacter(characterId) ?: throw NoOfflineDataException()

            val character = characterEntityMapper.map(dbCharacter)
            character
        }
    }

    override suspend fun killCharacter(characterId: Int): CharacterEntity {
        return withContext(dispatcherProvider.ioDispatcher) {
            characterDao.killCharacter(characterId)
            getCharacterDetails(characterId)
        }
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}
