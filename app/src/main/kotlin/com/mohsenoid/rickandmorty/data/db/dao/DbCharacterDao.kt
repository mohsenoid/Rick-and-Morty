package com.mohsenoid.rickandmorty.data.db.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mohsenoid.rickandmorty.data.db.entity.DbEntityCharacter

interface DbCharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: DbEntityCharacter)

    @Query(value = "SELECT * FROM characters WHERE _id IN (:characterIds)")
    suspend fun queryCharactersByIds(characterIds: List<Int>): List<DbEntityCharacter>

    @Query(value = "SELECT * FROM characters WHERE _id = :characterId LIMIT 1")
    suspend fun queryCharacter(characterId: Int): DbEntityCharacter?

    @Query(value = "UPDATE characters SET killedByUser = 1 WHERE _id = :characterId")
    suspend fun killCharacter(characterId: Int)

    suspend fun insertOrUpdateCharacter(character: DbEntityCharacter)
}
