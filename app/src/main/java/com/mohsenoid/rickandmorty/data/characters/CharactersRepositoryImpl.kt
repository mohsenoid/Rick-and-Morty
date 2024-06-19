package com.mohsenoid.rickandmorty.data.characters

import com.mohsenoid.rickandmorty.data.characters.dao.CharacterDao
import com.mohsenoid.rickandmorty.data.characters.mapper.CharacterMapper.toCharacter
import com.mohsenoid.rickandmorty.data.characters.mapper.CharacterMapper.toCharacterEntity
import com.mohsenoid.rickandmorty.data.remote.ApiService
import com.mohsenoid.rickandmorty.data.remote.model.CharacterRemoteModel
import com.mohsenoid.rickandmorty.domain.RepositoryGetResult
import com.mohsenoid.rickandmorty.domain.characters.CharactersRepository
import com.mohsenoid.rickandmorty.domain.characters.model.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class CharactersRepositoryImpl(
    private val apiService: ApiService,
    private val characterDao: CharacterDao,
) : CharactersRepository {

    private val charactersCache: MutableSet<Character> = mutableSetOf()

    override suspend fun getCharacters(characterIds: Set<Int>): RepositoryGetResult<Set<Character>> =
        withContext(Dispatchers.IO) {
            val cachedCharacterIds = charactersCache.map(Character::id).toSet()
            val uncachedCharacterIds = characterIds - cachedCharacterIds
            if (uncachedCharacterIds.isEmpty()) {
                return@withContext RepositoryGetResult.Success(
                    charactersCache.filter { it.id in characterIds }
                        .toSet(),
                )
            }

            val dbCharacters = characterDao.getCharacters(characterIds).map { it.toCharacter() }
            cacheCharacters(dbCharacters)
            val dbCharacterIds = dbCharacters.map(Character::id).toSet()
            val pendingCharacterIds = characterIds - dbCharacterIds
            if (pendingCharacterIds.isEmpty()) {
                return@withContext RepositoryGetResult.Success(
                    charactersCache.filter { it.id in characterIds }
                        .toSet(),
                )
            }

            val pendingCharacterIdsString = pendingCharacterIds.joinToString(",")
            val response =
                runCatching { apiService.getCharacters(pendingCharacterIdsString) }.getOrNull()
                    ?: return@withContext RepositoryGetResult.Failure.NoConnection("Connection Error!")
            val remoteCharacters: List<CharacterRemoteModel>? = response.body()
            if (response.isSuccessful && remoteCharacters != null) {
                val charactersEntity = remoteCharacters.map { it.toCharacterEntity() }
                charactersEntity.forEach { characterDao.insertCharacter(it) }
                val characters = charactersEntity.map { it.toCharacter() }
                cacheCharacters(characters)
                return@withContext RepositoryGetResult.Success(
                    charactersCache.filter { it.id in characterIds }
                        .toSet(),
                )
            } else {
                return@withContext RepositoryGetResult.Failure.Unknown(
                    response.message().ifEmpty { "Unknown Error" },
                )
            }
        }

    private fun cacheCharacters(dbCharacters: List<Character>) {
        charactersCache += dbCharacters
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
        val cachedCharacter = charactersCache.firstOrNull { it.id == characterId }
        if (cachedCharacter != null) {
            return RepositoryGetResult.Success(cachedCharacter)
        }

        return RepositoryGetResult.Failure.Unknown("No cached character")
    }

    private fun getDatabaseCharacter(characterId: Int): RepositoryGetResult<Character> {
        val dbCharacter = characterDao.getCharacter(characterId)?.toCharacter()
        if (dbCharacter != null) {
            charactersCache += dbCharacter
            return RepositoryGetResult.Success(dbCharacter)
        }

        return RepositoryGetResult.Failure.Unknown("No database character")
    }

    private suspend fun getRemoteCharacter(characterId: Int): RepositoryGetResult<Character> {
        val response =
            runCatching { apiService.getCharacter(characterId) }.getOrNull()
                ?: return RepositoryGetResult.Failure.NoConnection("No Connection Error")
        val remoteCharacter: CharacterRemoteModel? = response.body()
        if (response.isSuccessful && remoteCharacter != null) {
            val characterEntity = remoteCharacter.toCharacterEntity()
            characterDao.insertCharacter(characterEntity)
            val character = characterEntity.toCharacter()
            charactersCache += character
            return RepositoryGetResult.Success(character)
        } else {
            return RepositoryGetResult.Failure.Unknown(
                response.message().ifEmpty { "Unknown Error" },
            )
        }
    }
}
