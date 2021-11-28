package com.mohsenoid.rickandmorty.data.db

import androidx.room.Dao
import com.mohsenoid.rickandmorty.data.db.dto.DbCharacterModel

@Dao
abstract class DbCharacterDaoAbs : DbCharacterDao {

    override suspend fun insertOrUpdateCharacter(character: DbCharacterModel) {
        val oldCharacter: DbCharacterModel? = queryCharacter(characterId = character.id)

        insertCharacter(
            character = if (oldCharacter != null) {
                character.copy(killedByUser = oldCharacter.killedByUser)
            } else {
                character
            }
        )
    }
}
