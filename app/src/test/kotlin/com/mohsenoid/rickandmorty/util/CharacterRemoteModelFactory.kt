package com.mohsenoid.rickandmorty.util

import com.mohsenoid.rickandmorty.data.remote.model.CharacterRemoteModel
import com.mohsenoid.rickandmorty.data.remote.model.CharactersResponse
import com.mohsenoid.rickandmorty.data.remote.model.LocationRemoteModel
import com.mohsenoid.rickandmorty.data.remote.model.OriginRemoteModel

internal fun createCharactersResponse(
    characters: List<CharacterRemoteModel> =
        createCharactersRemoteModelList(
            count = 3,
        ),
): CharactersResponse = characters

internal fun createCharactersRemoteModelList(
    count: Int,
    id: (Int) -> Int = { it },
    name: (Int) -> String = { "name$it" },
    status: (Int) -> String = { "Alive" },
    species: (Int) -> String = { "species$it" },
    type: (Int) -> String = { "type$it" },
    gender: (Int) -> String = { "gender$it" },
    origin: (Int) -> OriginRemoteModel = {
        createOriginRemoteModel(
            name = "origin$it",
            url = "originUrl$it",
        )
    },
    location: (Int) -> LocationRemoteModel = {
        createLocationRemoteModel(
            name = "location$it",
            url = "locationUrl$it",
        )
    },
    image: (Int) -> String = { "image$it" },
    episode: (Int) -> List<String> = { emptyList() },
    url: (Int) -> String = { "" },
    created: (Int) -> String = { "" },
): List<CharacterRemoteModel> =
    buildList {
        repeat(count) { index ->
            val newCharacterRemoteModel =
                createCharacterRemoteModel(
                    id = id(index),
                    name = name(index),
                    status = status(index),
                    species = species(index),
                    type = type(index),
                    gender = gender(index),
                    origin = origin(index),
                    location = location(index),
                    image = image(index),
                    episode = episode(index),
                    url = url(index),
                    created = created(index),
                )
            add(newCharacterRemoteModel)
        }
    }

internal fun createCharacterRemoteModel(
    id: Int = 0,
    name: String = "name",
    status: String = "Alive",
    species: String = "species",
    type: String = "type",
    gender: String = "gender",
    origin: OriginRemoteModel = createOriginRemoteModel(),
    location: LocationRemoteModel = createLocationRemoteModel(),
    image: String = "image",
    episode: List<String> = emptyList(),
    url: String = "",
    created: String = "",
): CharacterRemoteModel =
    CharacterRemoteModel(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = origin,
        location = location,
        image = image,
        episode = episode,
        url = url,
        created = created,
    )

internal fun createOriginRemoteModel(
    name: String = "origin",
    url: String = "originUrl",
): OriginRemoteModel =
    OriginRemoteModel(
        name = name,
        url = url,
    )

internal fun createLocationRemoteModel(
    name: String = "location",
    url: String = "locationUrl",
): LocationRemoteModel =
    LocationRemoteModel(
        name = name,
        url = url,
    )
