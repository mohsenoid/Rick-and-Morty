package com.mohsenoid.rickandmorty.view.mapper

import com.mohsenoid.rickandmorty.domain.model.ModelCharacter
import com.mohsenoid.rickandmorty.view.model.ViewCharacterDetails
import com.mohsenoid.rickandmorty.view.model.ViewCharacterItem

fun ModelCharacter.toViewCharacterItem(onKill: () -> Unit, onClick: () -> Unit) = ViewCharacterItem(
    name = name,
    imageUrl = imageUrl,
    isAliveAndNotKilledByUser = isAliveAndNotKilledByUser,
    onKill = onKill,
    onClick = onClick,
)

fun ModelCharacter.toViewCharacterDetails() = ViewCharacterDetails(
    id = id,
    name = name,
    status = status,
    species = species,
    gender = gender,
    origin = origin.name,
    location = location.name,
    imageUrl = imageUrl,
    created = created,
    isKilledByUser = isKilledByUser,
)
