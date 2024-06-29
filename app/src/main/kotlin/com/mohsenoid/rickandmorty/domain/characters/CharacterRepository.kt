package com.mohsenoid.rickandmorty.domain.characters

import com.mohsenoid.rickandmorty.domain.characters.model.Character

interface CharacterRepository {
    suspend fun getCharacters(charactersIds: Set<Int>): Result<Set<Character>>

    suspend fun getCharacter(characterId: Int): Result<Character>

    suspend fun updateCharacterStatus(
        characterId: Int,
        isKilled: Boolean,
    ): Result<Unit>
}
