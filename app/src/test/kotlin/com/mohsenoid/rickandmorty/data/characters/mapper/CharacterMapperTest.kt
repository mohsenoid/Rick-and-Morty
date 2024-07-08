package com.mohsenoid.rickandmorty.data.characters.mapper

import com.mohsenoid.rickandmorty.data.characters.db.entity.CharacterEntity
import com.mohsenoid.rickandmorty.data.characters.mapper.CharacterMapper.toCharacter
import com.mohsenoid.rickandmorty.data.characters.mapper.CharacterMapper.toCharacterEntity
import com.mohsenoid.rickandmorty.data.characters.remote.model.CharacterRemoteModel
import com.mohsenoid.rickandmorty.data.characters.remote.model.LocationRemoteModel
import com.mohsenoid.rickandmorty.data.characters.remote.model.OriginRemoteModel
import com.mohsenoid.rickandmorty.domain.characters.model.Character
import kotlin.test.Test
import kotlin.test.assertEquals

class CharacterMapperTest {
    @Test
    fun toCharacterEntity() {
        // Given

        // When
        val actualCharacterEntity = TEST_CHARACTER_REMOTE_MODEL.toCharacterEntity()

        // Then
        assertEquals(TEST_CHARACTER_ENTITY, actualCharacterEntity)
    }

    @Test
    fun toCharacter() {
        // Given

        // When
        val actualCharacter = TEST_CHARACTER_ENTITY.toCharacter()

        // Then
        assertEquals(TEST_CHARACTER, actualCharacter)
    }

    companion object {
        private val TEST_CHARACTER_REMOTE_MODEL =
            CharacterRemoteModel(
                id = 1,
                name = "name",
                status = "dead",
                species = "species",
                type = "type",
                gender = "gender",
                origin = OriginRemoteModel(name = "origin", url = "originUrl"),
                location = LocationRemoteModel(name = "location", url = "locationUrl"),
                image = "image",
                episode =
                    listOf(
                        "https://rickandmortyapi.com/api/episode/1",
                        "https://rickandmortyapi.com/api/episode/2",
                        "https://rickandmortyapi.com/api/episode/3",
                    ),
                url = "url",
                created = "created",
            )

        private val TEST_CHARACTER_ENTITY =
            CharacterEntity(
                id = 1,
                name = "name",
                isAlive = false,
                isKilled = false,
                species = "species",
                type = "type",
                gender = "gender",
                origin = "origin",
                location = "location",
                image = "image",
            )

        private val TEST_CHARACTER =
            Character(
                id = 1,
                name = "name",
                isAlive = false,
                isKilled = false,
                species = "species",
                type = "type",
                gender = "gender",
                origin = "origin",
                location = "location",
                image = "image",
            )
    }
}
