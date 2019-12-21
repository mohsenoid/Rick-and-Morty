package com.mohsenoid.rickandmorty.data.db

import com.mohsenoid.rickandmorty.data.db.dto.DbCharacterModel
import com.mohsenoid.rickandmorty.data.db.dto.DbEpisodeModel

interface Db {

    fun insertEpisode(episode: DbEpisodeModel)

    fun queryAllEpisodes(page: Int): List<DbEpisodeModel>

    fun insertCharacter(character: DbCharacterModel)

    fun queryCharactersByIds(characterIds: List<Int>): List<DbCharacterModel>

    fun queryCharacter(characterId: Int): DbCharacterModel?

    fun killCharacter(characterId: Int)
}
