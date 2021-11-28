package com.mohsenoid.rickandmorty.data

import com.mohsenoid.rickandmorty.data.db.DbRickAndMorty
import com.mohsenoid.rickandmorty.data.db.entity.DbEntityCharacter
import com.mohsenoid.rickandmorty.data.db.entity.DbEntityEpisode
import com.mohsenoid.rickandmorty.data.exception.ServerException
import com.mohsenoid.rickandmorty.data.mapper.CharacterDbMapper
import com.mohsenoid.rickandmorty.data.mapper.CharacterEntityMapper
import com.mohsenoid.rickandmorty.data.mapper.EpisodeDbMapper
import com.mohsenoid.rickandmorty.data.mapper.EpisodeEntityMapper
import com.mohsenoid.rickandmorty.data.network.NetworkClient
import com.mohsenoid.rickandmorty.data.network.dto.NetworkCharacterModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkEpisodeModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkEpisodesResponse
import com.mohsenoid.rickandmorty.domain.Repository
import com.mohsenoid.rickandmorty.domain.model.ModelCharacter
import com.mohsenoid.rickandmorty.domain.model.ModelEpisode
import com.mohsenoid.rickandmorty.domain.model.PageQueryResult
import com.mohsenoid.rickandmorty.domain.model.QueryResult
import com.mohsenoid.rickandmorty.util.config.ConfigProvider
import retrofit2.Response

@Suppress("LongParameterList", "TooManyFunctions")
class RepositoryImpl internal constructor(
    private val db: DbRickAndMorty,
    private val networkClient: NetworkClient,
    private val configProvider: ConfigProvider,
) : Repository {

    override suspend fun getEpisodes(page: Int): PageQueryResult<List<ModelEpisode>> {
        if (configProvider.isOnline()) {
            try {
                val episodes: List<NetworkEpisodeModel> = fetchNetworkEpisodes(page)
                cacheNetworkEpisodes(episodes)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return queryDbEpisodes(page, PAGE_SIZE)
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
        episodes.map(EpisodeDbMapper::map)
            .forEach { db.insertEpisode(it) }
    }

    private suspend fun queryDbEpisodes(
        page: Int,
        pageSize: Int
    ): PageQueryResult<List<ModelEpisode>> {
        val dbEntityEpisodes: List<DbEntityEpisode> = db.queryAllEpisodesByPage(page, pageSize)
        val episodes: List<ModelEpisode> = dbEntityEpisodes.map(EpisodeEntityMapper::map)

        if (episodes.isEmpty()) return PageQueryResult.EndOfList

        return PageQueryResult.Successful(episodes)
    }

    override suspend fun getCharactersByIds(characterIds: List<Int>): QueryResult<List<ModelCharacter>> {
        if (configProvider.isOnline()) {
            try {
                val characters: List<NetworkCharacterModel> =
                    fetchNetworkCharactersByIds(characterIds)
                cacheNetworkCharacters(characters)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return queryDbCharactersByIds(characterIds)
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
        characters.map(CharacterDbMapper::map)
            .forEach { db.insertOrUpdateCharacter(it) }
    }

    private suspend fun queryDbCharactersByIds(characterIds: List<Int>): QueryResult<List<ModelCharacter>> {
        val dbCharacters: List<DbEntityCharacter> =
            db.queryCharactersByIds(characterIds)

        val characters: List<ModelCharacter> = dbCharacters.map(CharacterEntityMapper::map)

        if (characters.isEmpty()) QueryResult.NoCache

        return QueryResult.Successful(characters)
    }

    override suspend fun getCharacterDetails(characterId: Int): QueryResult<ModelCharacter> {
        if (configProvider.isOnline()) {
            try {
                val character: NetworkCharacterModel = fetchNetworkCharacterDetails(characterId)
                cacheNetworkCharacter(character)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return queryDbCharacterDetails(characterId)
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
        db.insertOrUpdateCharacter(CharacterDbMapper.map(character))
    }

    private suspend fun queryDbCharacterDetails(characterId: Int): QueryResult<ModelCharacter> {
        val dbCharacter: DbEntityCharacter =
            db.queryCharacter(characterId) ?: return QueryResult.NoCache

        return QueryResult.Successful(CharacterEntityMapper.map(dbCharacter))
    }

    override suspend fun killCharacter(characterId: Int): QueryResult<ModelCharacter> {
        db.killCharacter(characterId)
        return getCharacterDetails(characterId)
    }

    companion object {
        const val PAGE_SIZE: Int = 20
    }
}
