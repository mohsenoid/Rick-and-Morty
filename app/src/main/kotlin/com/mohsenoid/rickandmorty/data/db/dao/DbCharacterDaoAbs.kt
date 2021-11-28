package com.mohsenoid.rickandmorty.data.db.dao

import androidx.room.Dao
import com.mohsenoid.rickandmorty.data.db.model.DbCharacter

@Dao
internal abstract class DbCharacterDaoAbs : DbCharacterDao {

    override suspend fun insertOrUpdateCharacter(character: DbCharacter) {
        val oldCharacter: DbCharacter? = queryCharacter(characterId = character.id)

        insertCharacter(
            character = if (oldCharacter != null) {
                character.copy(killedByUser = oldCharacter.killedByUser)
            } else {
                character
            }
        )
    }
}
