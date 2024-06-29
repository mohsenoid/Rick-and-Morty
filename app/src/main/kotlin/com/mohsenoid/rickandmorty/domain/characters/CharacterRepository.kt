package com.mohsenoid.rickandmorty.domain.characters

import com.mohsenoid.rickandmorty.domain.RepositoryGetResult
import com.mohsenoid.rickandmorty.domain.characters.model.Character

interface CharacterRepository {
    suspend fun getCharacters(charactersIds: Set<Int>): RepositoryGetResult<Set<Character>>

    suspend fun getCharacter(characterId: Int): RepositoryGetResult<Character>

    suspend fun updateCharacterStatus(
        characterId: Int,
        isKilled: Boolean,
    ): Boolean
}
