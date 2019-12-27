package com.mohsenoid.rickandmorty.domain

import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity
import com.mohsenoid.rickandmorty.domain.entity.EpisodeEntity

interface Repository {

    suspend fun getEpisodes(page: Int): List<EpisodeEntity>

    suspend fun getCharactersByIds(characterIds: List<Int>): List<CharacterEntity>

    suspend fun getCharacterDetails(characterId: Int): CharacterEntity

    suspend fun killCharacter(characterId: Int): CharacterEntity
}
