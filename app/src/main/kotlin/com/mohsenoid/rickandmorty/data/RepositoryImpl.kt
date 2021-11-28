package com.mohsenoid.rickandmorty.data

import com.mohsenoid.rickandmorty.data.db.DbRickAndMorty
import com.mohsenoid.rickandmorty.data.db.entity.DbEntityCharacter
import com.mohsenoid.rickandmorty.data.db.entity.DbEntityEpisode
import com.mohsenoid.rickandmorty.data.exception.EndOfListException
import com.mohsenoid.rickandmorty.data.exception.NoOfflineDataException
import com.mohsenoid.rickandmorty.data.exception.ServerException
import com.mohsenoid.rickandmorty.data.mapper.Mapper
import com.mohsenoid.rickandmorty.data.network.NetworkClient
import com.mohsenoid.rickandmorty.data.network.dto.NetworkCharacterModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkEpisodeModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkEpisodesResponse
import com.mohsenoid.rickandmorty.domain.Repository
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity
import com.mohsenoid.rickandmorty.domain.entity.EpisodeEntity
import com.mohsenoid.rickandmorty.util.config.ConfigProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response

@Suppress("LongParameterList", "TooManyFunctions")
class RepositoryImpl internal constructor(
    private val db: DbRickAndMorty,
    private val networkClient: NetworkClient,
    private val ioDispatcher: CoroutineDispatcher,
    private val configProvider: ConfigProvider,
    private val episodeDbMapper: Mapper<NetworkEpisodeModel, DbEntityEpisode>,
    private val episodeEntityMapper: Mapper<DbEntityEpisode, EpisodeEntity>,
    private val characterDbMapper: Mapper<NetworkCharacterModel, DbEntityCharacter>,
    private val characterEntityMapper: Mapper<DbEntityCharacter, CharacterEntity>
) : Repository {

    override suspend fun getEpisodes(page: Int): List<EpisodeEntity> {
        return withContext(ioDispatcher) {
            if (configProvider.isOnline()) {
                try {
                    val episodes: List<NetworkEpisodeModel> = fetchNetworkEpisodes(page)
                    cacheNetworkEpisodes(episodes)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            queryDbEpisodes(page, PAGE_SIZE)
        }
    }

    private suspend fun fetchNetworkEpisodes(page: Int): List<NetworkEpisodeModel> {
        val networkEpisodesResponse: Response<NetworkEpisodesResponse> =
            networkClient.fetchEpisodes(page)

        if (networkEpisodesResponse.isSuccessful) {
            networkEpisodesResponse.body()?.let { networkEpisodes ->
                return networkEpisodes.results
            } ?: throw ServerException(
                code = networkEpisodesResponse.code(),
                error = "Response body is empty!"
            )
        } else {
            throw ServerException(
                code = networkEpisodesResponse.code(),
                error = networkEpisodesResponse.errorBody().toString()
            )
        }
    }

    private suspend fun cacheNetworkEpisodes(episodes: List<NetworkEpisodeModel>) {
        episodes.map(episodeDbMapper::map)
            .forEach { db.insertEpisode(it) }
    }

    private suspend fun queryDbEpisodes(page: Int, pageSize: Int): List<EpisodeEntity> {
        val dbEntityEpisodes: List<DbEntityEpisode> = db.queryAllEpisodesByPage(page, pageSize)
        val episodes: List<EpisodeEntity> = dbEntityEpisodes.map(episodeEntityMapper::map)

        if (episodes.isEmpty()) throw EndOfListException()

        return episodes
    }

    override suspend fun getCharactersByIds(characterIds: List<Int>): List<CharacterEntity> {
        return withContext(ioDispatcher) {
            if (configProvider.isOnline()) {
                try {
                    val characters: List<NetworkCharacterModel> =
                        fetchNetworkCharactersByIds(characterIds)
                    cacheNetworkCharacters(characters)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            return@withContext queryDbCharactersByIds(characterIds)
        }
    }

    private suspend fun fetchNetworkCharactersByIds(characterIds: List<Int>): List<NetworkCharacterModel> {
        val networkCharactersResponse: Response<List<NetworkCharacterModel>> =
            networkClient.fetchCharactersByIds(characterIds.joinToString(","))

        if (networkCharactersResponse.isSuccessful) {
            networkCharactersResponse.body()?.let { networkCharacters ->
                return networkCharacters
            } ?: throw ServerException(
                code = networkCharactersResponse.code(),
                error = "Response body is empty!"
            )
        } else {
            throw ServerException(
                code = networkCharactersResponse.code(),
                error = networkCharactersResponse.errorBody().toString()
            )
        }
    }

    private suspend fun cacheNetworkCharacters(characters: List<NetworkCharacterModel>) {
        characters.map(characterDbMapper::map)
            .forEach { db.insertOrUpdateCharacter(it) }
    }

    private suspend fun queryDbCharactersByIds(characterIds: List<Int>): List<CharacterEntity> {
        return withContext(ioDispatcher) {
            val dbCharacters: List<DbEntityCharacter> =
                db.queryCharactersByIds(characterIds)

            val characters: List<CharacterEntity> = dbCharacters.map(characterEntityMapper::map)

            if (characters.isEmpty()) throw NoOfflineDataException()

            return@withContext characters
        }
    }

    override suspend fun getCharacterDetails(characterId: Int): CharacterEntity {
        return withContext(ioDispatcher) {
            if (configProvider.isOnline()) {
                try {
                    val character: NetworkCharacterModel = fetchNetworkCharacterDetails(characterId)
                    cacheNetworkCharacter(character)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            return@withContext queryDbCharacterDetails(characterId)
        }
    }

    private suspend fun fetchNetworkCharacterDetails(characterId: Int): NetworkCharacterModel {
        val networkCharacterResponse = networkClient.fetchCharacterDetails(characterId)

        if (networkCharacterResponse.isSuccessful) {
            networkCharacterResponse.body()?.let { networkCharacter ->
                return networkCharacter
            } ?: throw ServerException(
                code = networkCharacterResponse.code(),
                error = "Response body is empty!"
            )
        } else {
            throw ServerException(
                code = networkCharacterResponse.code(),
                error = networkCharacterResponse.errorBody().toString()
            )
        }
    }

    private suspend fun cacheNetworkCharacter(character: NetworkCharacterModel) {
        db.insertOrUpdateCharacter(characterDbMapper.map(character))
    }

    private suspend fun queryDbCharacterDetails(characterId: Int): CharacterEntity {
        return withContext(ioDispatcher) {
            val dbCharacter: DbEntityCharacter =
                db.queryCharacter(characterId) ?: throw NoOfflineDataException()

            return@withContext characterEntityMapper.map(dbCharacter)
        }
    }

    override suspend fun killCharacter(characterId: Int): CharacterEntity {
        return withContext(ioDispatcher) {
            db.killCharacter(characterId)
            return@withContext getCharacterDetails(characterId)
        }
    }

    companion object {
        const val PAGE_SIZE: Int = 20
    }
}
