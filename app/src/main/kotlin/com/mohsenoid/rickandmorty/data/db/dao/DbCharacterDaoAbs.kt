package com.mohsenoid.rickandmorty.data.db.dao

import androidx.room.Dao
import com.mohsenoid.rickandmorty.data.db.entity.DbEntityCharacter

@Dao
abstract class DbCharacterDaoAbs : DbCharacterDao {

    override suspend fun insertOrUpdateCharacter(character: DbEntityCharacter) {
        val oldCharacter: DbEntityCharacter? = queryCharacter(characterId = character.id)

        insertCharacter(
            character = if (oldCharacter != null) {
                character.copy(killedByUser = oldCharacter.killedByUser)
            } else {
                character
            }
        )
    }
}
