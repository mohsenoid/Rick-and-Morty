package com.mohsenoid.rickandmorty.domain

import com.mohsenoid.rickandmorty.data.DataCallback
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity
import com.mohsenoid.rickandmorty.domain.entity.EpisodeEntity

interface Repository {

    fun queryEpisodes(
        page: Int,
        callback: DataCallback<List<EpisodeEntity>>?
    )

    fun queryCharactersByIds(
        characterIds: List<Int>,
        callback: DataCallback<List<CharacterEntity>>?
    )

    fun queryCharacterDetails(
        characterId: Int,
        callback: DataCallback<CharacterEntity>?
    )

    fun killCharacter(
        characterId: Int,
        callback: DataCallback<CharacterEntity>?
    )
}
