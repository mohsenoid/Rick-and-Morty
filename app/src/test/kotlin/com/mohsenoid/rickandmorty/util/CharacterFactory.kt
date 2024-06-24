package com.mohsenoid.rickandmorty.util

import com.mohsenoid.rickandmorty.domain.characters.model.Character

fun createCharactersList(
    count: Int,
    id: (Int) -> Int = { it },
    isAlive: (Int) -> Boolean = { true },
    isKilled: (Int) -> Boolean = { false },
    species: (Int) -> String = { "species$it" },
    type: (Int) -> String = { "type$it" },
    gender: (Int) -> String = { "gender$it" },
    origin: (Int) -> String = { "origin$it" },
    location: (Int) -> String = { "location$it" },
    image: (Int) -> String = { "image$it" },
): List<Character> =
    buildList {
        repeat(count) { index ->
            val newCharacter =
                createCharacter(
                    id = id(index),
                    isAlive = isAlive(index),
                    isKilled = isKilled(index),
                    species = species(index),
                    type = type(index),
                    gender = gender(index),
                    origin = origin(index),
                    location = location(index),
                    image = image(index),
                )
            add(newCharacter)
        }
    }

fun createCharacter(
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
): Character =
    Character(
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
