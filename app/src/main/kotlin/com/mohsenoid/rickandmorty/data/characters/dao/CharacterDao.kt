package com.mohsenoid.rickandmorty.data.characters.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mohsenoid.rickandmorty.data.characters.entity.CharacterEntity

@Dao
internal interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: CharacterEntity)

    @Query("SELECT * FROM characters WHERE id IN (:characterIds)")
    suspend fun getCharacters(characterIds: Set<Int>): List<CharacterEntity>

    @Query("SELECT * FROM characters WHERE id = :characterId")
    suspend fun getCharacter(characterId: Int): CharacterEntity?

    @Query("UPDATE characters SET is_killed = :isKilled WHERE id = :characterId")
    suspend fun updateCharacterStatus(
        characterId: Int,
        isKilled: Boolean,
    ): Int
}
