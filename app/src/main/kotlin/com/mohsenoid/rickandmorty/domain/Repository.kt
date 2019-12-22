package com.mohsenoid.rickandmorty.domain

import com.mohsenoid.rickandmorty.data.DataCallback
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity
import com.mohsenoid.rickandmorty.domain.entity.EpisodeEntity

interface Repository {

    suspend fun queryEpisodes(
        page: Int,
        callback: DataCallback<List<EpisodeEntity>>?
    )

    suspend fun queryCharactersByIds(
        characterIds: List<Int>,
        callback: DataCallback<List<CharacterEntity>>?
    )

    suspend fun queryCharacterDetails(
        characterId: Int,
        callback: DataCallback<CharacterEntity>?
    )

    suspend fun killCharacter(
        characterId: Int,
        callback: DataCallback<CharacterEntity>?
    )
}