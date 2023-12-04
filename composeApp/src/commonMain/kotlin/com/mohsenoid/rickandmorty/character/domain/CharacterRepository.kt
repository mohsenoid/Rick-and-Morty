package com.mohsenoid.rickandmorty.character.domain

import com.mohsenoid.rickandmorty.character.domain.model.Character

interface CharacterRepository {
    suspend fun getCharacter(characterId: Int): Character
    fun close()
}
