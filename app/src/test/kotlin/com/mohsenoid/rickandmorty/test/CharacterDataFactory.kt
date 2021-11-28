package com.mohsenoid.rickandmorty.test

import com.mohsenoid.rickandmorty.data.api.model.ApiCharacter
import com.mohsenoid.rickandmorty.data.db.model.DbCharacter
import com.mohsenoid.rickandmorty.domain.model.ModelCharacter

object CharacterDataFactory {

    object Db {

        fun makeCharacter(
            characterId: Int = DataFactory.randomInt(),
            status: String = DataFactory.randomString(),
            isAlive: Boolean = DataFactory.randomBoolean(),
            isKilledByUser: Boolean = false
        ): DbCharacter {
            return DbCharacter(
                id = characterId,
                name = DataFactory.randomString(),
                status = status,
                statusAlive = isAlive,
                species = DataFactory.randomString(),
                type = DataFactory.randomString(),
                gender = DataFactory.randomString(),
                origin = OriginDataFactory.Db.makeOrigin(),
                location = LocationDataFactory.Db.makeLocation(),
                image = DataFactory.randomString(),
                episodeIds = DataFactory.randomIntList(count = 5),
                url = DataFactory.randomString(),
                created = DataFactory.randomString(),
                killedByUser = isKilledByUser
            )
        }

        fun makeCharacters(count: Int): List<DbCharacter> {
            val characters: MutableList<DbCharacter> = ArrayList()
            for (i in 0 until count) {
                val character: DbCharacter = makeCharacter()
                characters.add(character)
            }
            return characters
        }
    }

    object Network {

        fun makeCharacter(characterId: Int = DataFactory.randomInt()): ApiCharacter {
            return ApiCharacter(
                id = characterId,
                name = DataFactory.randomString(),
                status = DataFactory.randomString(),
                species = DataFactory.randomString(),
                type = DataFactory.randomString(),
                gender = DataFactory.randomString(),
                origin = OriginDataFactory.Network.makeOrigin(),
                location = LocationDataFactory.Network.makeLocation(),
                image = DataFactory.randomString(),
                episodes = DataFactory.randomIntList(count = 5).map { "${DataFactory.randomString()}/$it" },
                url = DataFactory.randomString(),
                created = DataFactory.randomString()
            )
        }

        fun makeCharacters(count: Int): List<ApiCharacter> {
            val characters: MutableList<ApiCharacter> = ArrayList()
            for (i: Int in 0 until count) {
                val character: ApiCharacter = makeCharacter()
                characters.add(character)
            }
            return characters
        }
    }

    object Entity {

        fun makeCharacter(
            characterId: Int = DataFactory.randomInt(),
            status: String = DataFactory.randomString(),
            isAlive: Boolean = DataFactory.randomBoolean(),
            isKilledByUser: Boolean = false
        ): ModelCharacter {
            return ModelCharacter(
                id = characterId,
                name = DataFactory.randomString(),
                status = status,
                statusAlive = isAlive,
                species = DataFactory.randomString(),
                type = DataFactory.randomString(),
                gender = DataFactory.randomString(),
                origin = OriginDataFactory.Entity.makeOrigin(),
                location = LocationDataFactory.Entity.makeEntity(),
                imageUrl = DataFactory.randomString(),
                episodeIds = DataFactory.randomIntList(count = 5),
                url = DataFactory.randomString(),
                created = DataFactory.randomString(),
                killedByUser = isKilledByUser
            )
        }

        fun makeCharacters(count: Int): List<ModelCharacter> {
            val characters: MutableList<ModelCharacter> = ArrayList()
            for (i: Int in 0 until count) {
                val character: ModelCharacter = makeCharacter()
                characters.add(character)
            }
            return characters
        }
    }
}
