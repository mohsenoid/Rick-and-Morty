package com.mohsenoid.rickandmorty.data.characters

import com.mohsenoid.rickandmorty.data.characters.db.dao.CharacterDao
import com.mohsenoid.rickandmorty.data.characters.mapper.CharacterMapper.toCharacter
import com.mohsenoid.rickandmorty.data.characters.mapper.CharacterMapper.toCharacterEntity
import com.mohsenoid.rickandmorty.data.characters.remote.CharacterApiService
import com.mohsenoid.rickandmorty.data.characters.remote.model.CharacterRemoteModel
import com.mohsenoid.rickandmorty.domain.NoInternetConnectionException
import com.mohsenoid.rickandmorty.domain.characters.CharacterRepository
import com.mohsenoid.rickandmorty.domain.characters.model.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.SortedMap

internal class CharacterRepositoryImpl(
    private val characterApiService: CharacterApiService,
    private val characterDao: CharacterDao,
) : CharacterRepository {
    private val charactersCache: SortedMap<Int, Character> = sortedMapOf()

    override suspend fun getCharacters(charactersIds: Set<Int>): Result<List<Character>> =
        withContext(Dispatchers.IO) {
            getCharactersFromCache(charactersIds)
                ?: getCharactersFromDb(charactersIds)
                ?: getCharactersFromRemote(charactersIds)
        }

    private fun getCharactersFromCache(charactersIds: Set<Int>): Result<List<Character>>? {
        return if (charactersCache.keys.containsAll(charactersIds)) {
            Result.success(charactersCache.filterKeys { it in charactersIds }.values.toList())
        } else {
            null
        }
    }

    private suspend fun getCharactersFromDb(charactersIds: Set<Int>): Result<List<Character>>? {
        val missingCharactersIds = charactersIds - charactersCache.keys

        val dbCharacters = characterDao.getCharacters(missingCharactersIds).map { it.toCharacter() }
        cacheCharacters(dbCharacters)

        return getCharactersFromCache(charactersIds)
    }

    private suspend fun getCharactersFromRemote(charactersIds: Set<Int>): Result<List<Character>>  {
            val missingCharactersIds = charactersIds - charactersCache.keys
            val missingCharactersIdsString = missingCharactersIds.joinToString(",")

           return try {
                val response = characterApiService.getCharacters(missingCharactersIdsString)
                val remoteCharacters: List<CharacterRemoteModel>? = response.body()
                if (response.isSuccessful && remoteCharacters != null) {
                    handleSuccessfulRemoteResponse(remoteCharacters)
                    getCharactersFromCache(charactersIds)!! // All characters should be cached
                } else {
                    Result.failure(Exception(response.message().ifEmpty { "Unknown Error" }))
                }
            } catch (e: UnknownHostException) {
                Result.failure(NoInternetConnectionException(e.message))
            } catch (e: SocketTimeoutException) {
                Result.failure(NoInternetConnectionException(e.message))
            }
        }

    private suspend fun handleSuccessfulRemoteResponse(remoteCharacters: List<CharacterRemoteModel>) {
        val charactersEntity = remoteCharacters.map { it.toCharacterEntity() }
        charactersEntity.forEach { characterDao.insertCharacter(it) }
        val characters = charactersEntity.map { it.toCharacter() }
        cacheCharacters(characters)
    }

    private fun cacheCharacters(dbCharacters: List<Character>) {
        charactersCache.putAll(dbCharacters.associateBy { it.id })
    }

    override suspend fun getCharacter(characterId: Int): Result<Character> =
        withContext(Dispatchers.IO) {
            getCharacterFromCache(characterId)
                ?: getCharacterFromDb(characterId)
                ?: getCharacterFromRemote(characterId)
        }

    private fun getCharacterFromCache(characterId: Int): Result<Character>? {
        return charactersCache[characterId]?.let { Result.success(it) }
    }

    private suspend fun getCharacterFromDb(characterId: Int): Result<Character>? {
        val dbCharacter = characterDao.getCharacter(characterId)?.toCharacter()
        return dbCharacter?.let {
            charactersCache += it.id to it
            Result.success(it)
        }
    }

    private suspend fun getCharacterFromRemote(characterId: Int): Result<Character> {
          return  try {
                val response = characterApiService.getCharacter(characterId)
                val remoteCharacter: CharacterRemoteModel? = response.body()
                if (response.isSuccessful && remoteCharacter != null) {
                    val characterEntity = remoteCharacter.toCharacterEntity()
                    characterDao.insertCharacter(characterEntity)
                    val character = characterEntity.toCharacter()
                    charactersCache += character.id to character
                    Result.success(character)
                } else {
                    Result.failure(Exception(response.message().ifEmpty { "Unknown Error" }))
                }
            } catch (e: UnknownHostException) {
                Result.failure(NoInternetConnectionException(e.message))
            } catch (e: SocketTimeoutException) {
                Result.failure(NoInternetConnectionException(e.message))
            }
        }

    override suspend fun updateCharacterStatus(
        characterId: Int,
        isKilled: Boolean,
    ): Result<Unit> =
        withContext(Dispatchers.IO) {
            if (characterDao.updateCharacterStatus(characterId, isKilled) == 1) {
                updateCharacterInCache(characterId, isKilled)
                Result.success(Unit)
            } else {
                Result.failure(Exception("Unable to update character status"))
            }
        }

    private fun updateCharacterInCache(
        characterId: Int,
        isKilled: Boolean,
    ) {
        charactersCache[characterId]?.let {
            charactersCache[characterId] = it.copy(isKilled = isKilled)
        }
    }
}
