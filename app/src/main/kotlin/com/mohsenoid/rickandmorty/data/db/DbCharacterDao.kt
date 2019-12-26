package com.mohsenoid.rickandmorty.data.db

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mohsenoid.rickandmorty.data.db.dto.DbCharacterModel

interface DbCharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: DbCharacterModel)

    @Query("SELECT * FROM characters WHERE _id IN (:characterIds)")
    suspend fun queryCharactersByIds(characterIds: List<Int>): List<DbCharacterModel>

    @Query("SELECT * FROM characters WHERE _id = :characterId LIMIT 1")
    suspend fun queryCharacter(characterId: Int): DbCharacterModel?

    @Query("UPDATE characters SET killedByUser = 1 WHERE _id = :characterId")
    suspend fun killCharacter(characterId: Int)

    suspend fun insertOrUpdateCharacter(character: DbCharacterModel)
}
