package com.mohsenoid.rickandmorty.data.characters.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mohsenoid.rickandmorty.data.characters.entity.CharacterEntity

@Dao
internal interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(character: CharacterEntity)

    @Query("SELECT * FROM characters WHERE id IN (:characterIds)")
    fun getCharacters(characterIds: Set<Int>): List<CharacterEntity>

    @Query("SELECT * FROM characters WHERE id = :characterId")
    fun getCharacter(characterId: Int): CharacterEntity?

    @Update
    fun updateCharacter(character: CharacterEntity)

    @Query("UPDATE characters SET is_killed = :isKilled WHERE id = :characterId")
    fun updateCharacterStatus(
        characterId: Int,
        isKilled: Boolean,
    ): Int
}
