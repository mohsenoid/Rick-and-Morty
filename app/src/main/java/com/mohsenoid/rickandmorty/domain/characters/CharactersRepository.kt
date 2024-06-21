package com.mohsenoid.rickandmorty.domain.characters

import com.mohsenoid.rickandmorty.domain.RepositoryGetResult
import com.mohsenoid.rickandmorty.domain.characters.model.Character

interface CharactersRepository {
    suspend fun getCharacters(charactersIds: Set<Int>): RepositoryGetResult<Set<Character>>

    suspend fun getCharacter(characterId: Int): RepositoryGetResult<Character>

    suspend fun updateCharacterStatus(
        characterId: Int,
        isKilled: Boolean,
    ): RepositoryGetResult<Character>
}
