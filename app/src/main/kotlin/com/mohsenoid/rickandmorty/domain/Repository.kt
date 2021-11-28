package com.mohsenoid.rickandmorty.domain

import com.mohsenoid.rickandmorty.domain.model.ModelCharacter
import com.mohsenoid.rickandmorty.domain.model.ModelEpisode
import com.mohsenoid.rickandmorty.domain.model.PageQueryResult
import com.mohsenoid.rickandmorty.domain.model.QueryResult

interface Repository {

    suspend fun getEpisodes(page: Int): PageQueryResult<List<ModelEpisode>>

    suspend fun getCharactersByIds(characterIds: List<Int>): QueryResult<List<ModelCharacter>>

    suspend fun getCharacterDetails(characterId: Int): QueryResult<ModelCharacter>

    suspend fun killCharacter(characterId: Int): QueryResult<ModelCharacter>
}
