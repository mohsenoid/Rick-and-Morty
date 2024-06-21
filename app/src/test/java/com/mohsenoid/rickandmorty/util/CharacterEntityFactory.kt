package com.mohsenoid.rickandmorty.util

import com.mohsenoid.rickandmorty.data.characters.entity.CharacterEntity

internal fun createCharacterEntitiesList(
    count: Int,
    id: (Int) -> Int = { it },
    name: (Int) -> String = { "name$it" },
    isAlive: (Int) -> Boolean = { true },
    isKilled: (Int) -> Boolean = { false },
    species: (Int) -> String = { "species$it" },
    type: (Int) -> String = { "type$it" },
    gender: (Int) -> String = { "gender$it" },
    origin: (Int) -> String = { "origin$it" },
    location: (Int) -> String = { "location$it" },
    image: (Int) -> String = { "image$it" },
): List<CharacterEntity> =
    buildList {
        repeat(count) { index ->
            val newCharacterEntity =
                createCharacterEntity(
                    id = id(index),
                    name = name(index),
                    isAlive = isAlive(index),
                    isKilled = isKilled(index),
                    species = species(index),
                    type = type(index),
                    gender = gender(index),
                    origin = origin(index),
                    location = location(index),
                    image = image(index),
                )
            add(newCharacterEntity)
        }
    }

internal fun createCharacterEntity(
    id: Int = 0,
    name: String = "name",
    isAlive: Boolean = true,
    isKilled: Boolean = false,
    species: String = "species",
    type: String = "type",
    gender: String = "gender",
    origin: String = "origin",
    location: String = "location",
    image: String = "image",
): CharacterEntity =
    CharacterEntity(
        id = id,
        name = name,
        isAlive = isAlive,
        isKilled = isKilled,
        species = species,
        type = type,
        gender = gender,
        origin = origin,
        location = location,
        image = image,
    )
