package com.mohsenoid.rickandmorty.test

import com.mohsenoid.rickandmorty.data.db.dto.DbCharacterModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkCharacterModel
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity

object CharacterDataFactory {

    object Db {

        fun makeCharacter(
            characterId: Int = DataFactory.randomInt(),
            status: String = DataFactory.randomString(),
            isAlive: Boolean = DataFactory.randomBoolean(),
            isKilledByUser: Boolean = false
        ): DbCharacterModel {
            return DbCharacterModel(
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

        fun makeCharacters(count: Int): List<DbCharacterModel> {
            val characters: MutableList<DbCharacterModel> = ArrayList()
            for (i in 0 until count) {
                val character: DbCharacterModel = makeCharacter()
                characters.add(character)
            }
            return characters
        }
    }

    object Network {

        fun makeCharacter(characterId: Int = DataFactory.randomInt()): NetworkCharacterModel {
            return NetworkCharacterModel(
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

        fun makeCharacters(count: Int): List<NetworkCharacterModel> {
            val characters: MutableList<NetworkCharacterModel> = ArrayList()
            for (i: Int in 0 until count) {
                val character: NetworkCharacterModel = makeCharacter()
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
        ): CharacterEntity {
            return CharacterEntity(
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

        fun makeCharacters(count: Int): List<CharacterEntity> {
            val characters: MutableList<CharacterEntity> = ArrayList()
            for (i: Int in 0 until count) {
                val character: CharacterEntity = makeCharacter()
                characters.add(character)
            }
            return characters
        }
    }
}
