package com.mohsenoid.rickandmorty.character.data

import com.mohsenoid.rickandmorty.character.data.model.CharacterResponse
import com.mohsenoid.rickandmorty.character.domain.CharacterRepository
import com.mohsenoid.rickandmorty.character.domain.model.Character

internal class CharacterRepositoryImpl(
    private val remote: CharacterRemoteDataSource,
) : CharacterRepository {

    override suspend fun getCharacter(characterId: Int): Character {
        val characterResponse = remote.fetchCharacter(characterId)
        return characterResponse.toCharacter()
    }

    private fun CharacterResponse.toCharacter() = Character(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = origin.name,
        location = location.name,
        image = image,
        created = created,
    )

    override fun close() {
        remote.close()
    }
}
