package com.mohsenoid.rickandmorty.data.characters.mapper

import com.mohsenoid.rickandmorty.data.characters.entity.CharacterEntity
import com.mohsenoid.rickandmorty.data.remote.model.CharacterRemoteModel
import com.mohsenoid.rickandmorty.domain.characters.model.Character

internal object CharacterMapper {

    fun CharacterRemoteModel.toCharacterEntity() = CharacterEntity(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = origin.name,
        location = location.name,
        image = image,
    )

    fun CharacterEntity.toCharacter() = Character(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = origin,
        location = location,
        image = image,
    )
}