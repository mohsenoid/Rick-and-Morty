package com.mohsenoid.rickandmorty.data.characters

import com.mohsenoid.rickandmorty.data.characters.dao.CharacterDao
import com.mohsenoid.rickandmorty.data.characters.mapper.CharacterMapper.toCharacter
import com.mohsenoid.rickandmorty.data.characters.mapper.CharacterMapper.toCharacterEntity
import com.mohsenoid.rickandmorty.data.remote.ApiService
import com.mohsenoid.rickandmorty.data.remote.model.CharacterRemoteModel
import com.mohsenoid.rickandmorty.domain.RepositoryGetResult
import com.mohsenoid.rickandmorty.domain.characters.CharacterRepository
import com.mohsenoid.rickandmorty.domain.characters.model.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.SortedMap

internal class CharacterRepositoryImpl(
    private val apiService: ApiService,
    private val characterDao: CharacterDao,
) : CharacterRepository {
    private val charactersCache: SortedMap<Int, Character> = sortedMapOf()

    override suspend fun getCharacters(charactersIds: Set<Int>): RepositoryGetResult<Set<Character>> =
        withContext(Dispatchers.IO) {
            val getCachedCharactersResult = getCachedCharacters(charactersIds)
            if (getCachedCharactersResult is RepositoryGetResult.Success) {
                return@withContext getCachedCharactersResult
            }

            val getDbCharactersResult = getDbCharacters(charactersIds)
            if (getDbCharactersResult is RepositoryGetResult.Success) {
                return@withContext getDbCharactersResult
            }

            return@withContext getRemoteCharacters(charactersIds)
        }

    private fun getCachedCharacters(charactersIds: Set<Int>): RepositoryGetResult<Set<Character>> {
        if (charactersCache.keys.containsAll(charactersIds)) {
            return RepositoryGetResult.Success(charactersCache.filterKeys { it in charactersIds }.values.toSet())
        }

        return RepositoryGetResult.Failure.Unknown("Not all characters are cached")
    }

    private fun getDbCharacters(charactersIds: Set<Int>): RepositoryGetResult<Set<Character>> {
        val pendingCharactersIds = charactersIds - charactersCache.keys

        val dbCharacters = characterDao.getCharacters(pendingCharactersIds).map { it.toCharacter() }
        cacheCharacters(dbCharacters)

        return getCachedCharacters(charactersIds)
    }

    private suspend fun getRemoteCharacters(charactersIds: Set<Int>): RepositoryGetResult<Set<Character>> {
        val pendingCharactersIds = charactersIds - charactersCache.keys

        val pendingCharactersIdsString = pendingCharactersIds.joinToString(",")
        val response =
            runCatching { apiService.getCharacters(pendingCharactersIdsString) }.getOrNull()
                ?: return RepositoryGetResult.Failure.NoConnection("Connection Error!")
        val remoteCharacters: List<CharacterRemoteModel>? = response.body()
        return if (response.isSuccessful && remoteCharacters != null) {
            val charactersEntity = remoteCharacters.map { it.toCharacterEntity() }
            charactersEntity.forEach { characterDao.insertCharacter(it) }
            val characters = charactersEntity.map { it.toCharacter() }
            cacheCharacters(characters)
            getCachedCharacters(charactersIds)
        } else {
            RepositoryGetResult.Failure.Unknown(
                response.message().ifEmpty { "Unknown Error" },
            )
        }
    }

    private fun cacheCharacters(dbCharacters: List<Character>) {
        charactersCache.putAll(dbCharacters.associateBy { it.id })
    }

    override suspend fun getCharacter(characterId: Int): RepositoryGetResult<Character> =
        withContext(Dispatchers.IO) {
            val getCachedCharacterResult = getCachedCharacter(characterId)
            if (getCachedCharacterResult is RepositoryGetResult.Success) {
                return@withContext getCachedCharacterResult
            }

            val getDatabaseCharacterResult = getDatabaseCharacter(characterId)
            if (getDatabaseCharacterResult is RepositoryGetResult.Success) {
                return@withContext getDatabaseCharacterResult
            }

            val getRemoteCharacterResult = getRemoteCharacter(characterId)
            return@withContext getRemoteCharacterResult
        }

    private fun getCachedCharacter(characterId: Int): RepositoryGetResult<Character> {
        val cachedCharacter = charactersCache[characterId]
        if (cachedCharacter != null) {
            return RepositoryGetResult.Success(cachedCharacter)
        }

        return RepositoryGetResult.Failure.Unknown("No cached character")
    }

    private fun getDatabaseCharacter(characterId: Int): RepositoryGetResult<Character> {
        val dbCharacter = characterDao.getCharacter(characterId)?.toCharacter()
        if (dbCharacter != null) {
            charactersCache += dbCharacter.id to dbCharacter
            return RepositoryGetResult.Success(dbCharacter)
        }

        return RepositoryGetResult.Failure.Unknown("No database character")
    }

    private suspend fun getRemoteCharacter(characterId: Int): RepositoryGetResult<Character> {
        val response =
            runCatching { apiService.getCharacter(characterId) }.getOrNull()
                ?: return RepositoryGetResult.Failure.NoConnection("No Connection Error")
        val remoteCharacter: CharacterRemoteModel? = response.body()
        return if (response.isSuccessful && remoteCharacter != null) {
            val characterEntity = remoteCharacter.toCharacterEntity()
            characterDao.insertCharacter(characterEntity)
            val character = characterEntity.toCharacter()
            charactersCache += character.id to character
            RepositoryGetResult.Success(character)
        } else {
            RepositoryGetResult.Failure.Unknown(response.message().ifEmpty { "Unknown Error" })
        }
    }

    override suspend fun updateCharacterStatus(
        characterId: Int,
        isKilled: Boolean,
    ): RepositoryGetResult<Character> =
        withContext(Dispatchers.IO) {
            val updatedSuccessfully = characterDao.updateCharacterStatus(characterId, isKilled) == 1
            if (updatedSuccessfully) {
                charactersCache.remove(characterId)
                return@withContext getCharacter(characterId)
            }

            return@withContext RepositoryGetResult.Failure.Unknown(message = "Error updating character status!")
        }
}
