package com.mohsenoid.rickandmorty.data

import com.mohsenoid.rickandmorty.data.api.ApiRickAndMorty
import com.mohsenoid.rickandmorty.data.api.model.ApiCharacter
import com.mohsenoid.rickandmorty.data.api.model.ApiEpisode
import com.mohsenoid.rickandmorty.data.api.model.ApiResult
import com.mohsenoid.rickandmorty.data.db.DbRickAndMorty
import com.mohsenoid.rickandmorty.data.db.model.DbCharacter
import com.mohsenoid.rickandmorty.data.db.model.DbEpisode
import com.mohsenoid.rickandmorty.data.mapper.toDbCharacter
import com.mohsenoid.rickandmorty.data.mapper.toDbEpisode
import com.mohsenoid.rickandmorty.data.mapper.toModelCharacter
import com.mohsenoid.rickandmorty.data.mapper.toModelEpisode
import com.mohsenoid.rickandmorty.domain.Repository
import com.mohsenoid.rickandmorty.domain.model.ModelCharacter
import com.mohsenoid.rickandmorty.domain.model.ModelEpisode
import com.mohsenoid.rickandmorty.domain.model.PageQueryResult
import com.mohsenoid.rickandmorty.domain.model.QueryResult
import com.mohsenoid.rickandmorty.util.StatusProvider

@Suppress("TooManyFunctions")
class RepositoryImpl internal constructor(
    private val db: DbRickAndMorty,
    private val api: ApiRickAndMorty,
    private val statusProvider: StatusProvider,
) : Repository {

    override suspend fun getEpisodes(page: Int): PageQueryResult<List<ModelEpisode>> {
        if (statusProvider.isOnline()) {
            val result = fetchNetworkEpisodes(page)
            if (result is PageQueryResult.Successful) cacheNetworkEpisodes(result.data)
        }

        return queryDbEpisodes(page, PAGE_SIZE)
    }

    private suspend fun fetchNetworkEpisodes(page: Int): PageQueryResult<List<ApiEpisode>> =
        when (val result = api.fetchEpisodes(page)) {
            is ApiResult.Success -> PageQueryResult.Successful(result.data.results)
            is ApiResult.Error.ServerError,
            is ApiResult.Error.UnknownError -> PageQueryResult.Error
        }

    private suspend fun cacheNetworkEpisodes(episodes: List<ApiEpisode>) {
        episodes.map(ApiEpisode::toDbEpisode)
            .forEach { db.insertEpisode(it) }
    }

    private suspend fun queryDbEpisodes(
        page: Int,
        pageSize: Int,
    ): PageQueryResult<List<ModelEpisode>> {
        val dbEntityEpisodes: List<DbEpisode> = db.queryAllEpisodesByPage(page, pageSize)
        val episodes: List<ModelEpisode> = dbEntityEpisodes.map(DbEpisode::toModelEpisode)

        if (episodes.isEmpty()) return PageQueryResult.EndOfList

        return PageQueryResult.Successful(episodes)
    }

    override suspend fun getCharactersByIds(
        characterIds: List<Int>,
    ): QueryResult<List<ModelCharacter>> {
        if (statusProvider.isOnline()) {
            val result = fetchNetworkCharactersByIds(characterIds)
            if (result is QueryResult.Successful) cacheNetworkCharacters(result.data)
        }

        return queryDbCharactersByIds(characterIds)
    }

    private suspend fun fetchNetworkCharactersByIds(
        characterIds: List<Int>,
    ): QueryResult<List<ApiCharacter>> =
        when (val result = api.fetchCharactersByIds(characterIds.joinToString(","))) {
            is ApiResult.Success -> QueryResult.Successful(result.data)
            is ApiResult.Error.ServerError,
            is ApiResult.Error.UnknownError -> QueryResult.Error
        }

    private suspend fun cacheNetworkCharacters(characters: List<ApiCharacter>) {
        characters.map(ApiCharacter::toDbCharacter)
            .forEach { db.insertOrUpdateCharacter(it) }
    }

    private suspend fun queryDbCharactersByIds(
        characterIds: List<Int>,
    ): QueryResult<List<ModelCharacter>> {
        val dbCharacters: List<DbCharacter> = db.queryCharactersByIds(characterIds)

        val characters: List<ModelCharacter> = dbCharacters.map(DbCharacter::toModelCharacter)

        if (characters.isEmpty()) QueryResult.NoCache

        return QueryResult.Successful(characters)
    }

    override suspend fun getCharacterDetails(characterId: Int): QueryResult<ModelCharacter> {
        if (statusProvider.isOnline()) {
            val result = fetchNetworkCharacterDetails(characterId)
            if (result is QueryResult.Successful) cacheNetworkCharacter(result.data)
        }

        return queryDbCharacterDetails(characterId)
    }

    private suspend fun fetchNetworkCharacterDetails(characterId: Int): QueryResult<ApiCharacter> =
        when (val result = api.fetchCharacterDetails(characterId)) {
            is ApiResult.Success -> QueryResult.Successful(result.data)
            is ApiResult.Error.ServerError,
            is ApiResult.Error.UnknownError -> QueryResult.Error
        }

    private suspend fun cacheNetworkCharacter(character: ApiCharacter) {
        db.insertOrUpdateCharacter(character.toDbCharacter())
    }

    private suspend fun queryDbCharacterDetails(characterId: Int): QueryResult<ModelCharacter> {
        val dbCharacter: DbCharacter =
            db.queryCharacter(characterId) ?: return QueryResult.NoCache

        return QueryResult.Successful(dbCharacter.toModelCharacter())
    }

    override suspend fun killCharacter(characterId: Int): QueryResult<ModelCharacter> {
        db.killCharacter(characterId)
        return getCharacterDetails(characterId)
    }

    companion object {
        const val PAGE_SIZE: Int = 20
    }
}
