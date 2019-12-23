package com.mohsenoid.rickandmorty.test

import com.mohsenoid.rickandmorty.data.db.dto.DbCharacterModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkCharacterModel
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity
import java.util.ArrayList

object CharacterDataFactory {

    object Db {

        fun makeDbCharacterModel(
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
                origin = OriginDataFactory.Db.makeDbOriginModel(),
                location = LocationDataFactory.Db.makeDbLocationModel(),
                image = DataFactory.randomString(),
                serializedEpisodes = "${DataFactory.randomInt()},${DataFactory.randomInt()},${DataFactory.randomInt()}",
                url = DataFactory.randomString(),
                created = DataFactory.randomString(),
                killedByUser = isKilledByUser
            )
        }

        fun makeDbCharactersModelList(count: Int): List<DbCharacterModel> {
            val characters: MutableList<DbCharacterModel> =
                ArrayList()
            for (i in 0 until count) {
                val character =
                    makeDbCharacterModel()
                characters.add(character)
            }
            return characters
        }
    }

    object Network {

        fun makeNetworkCharacterModel(characterId: Int = DataFactory.randomInt()): NetworkCharacterModel {
            return NetworkCharacterModel(
                characterId,
                DataFactory.randomString(),
                DataFactory.randomString(),
                DataFactory.randomString(),
                DataFactory.randomString(),
                DataFactory.randomString(),
                OriginDataFactory.Network.makeNetworkOriginModel(),
                LocationDataFactory.Network.makeNetworkLocationModel(),
                DataFactory.randomString(),
                DataFactory.randomStringList(5),
                DataFactory.randomString(),
                DataFactory.randomString()
            )
        }

        fun makeNetworkCharactersModelList(count: Int): List<NetworkCharacterModel> {
            val characters: MutableList<NetworkCharacterModel> =
                ArrayList()
            for (i in 0 until count) {
                val character =
                    makeNetworkCharacterModel()
                characters.add(character)
            }
            return characters
        }
    }

    object Entity {

        fun makeCharacterEntity(
            characterId: Int = DataFactory.randomInt(),
            status: String = DataFactory.randomString(),
            isAlive: Boolean = DataFactory.randomBoolean(),
            isKilledByUser: Boolean = false
        ): CharacterEntity {
            return CharacterEntity(
                characterId,
                DataFactory.randomString(),
                status,
                isAlive,
                DataFactory.randomString(),
                DataFactory.randomString(),
                DataFactory.randomString(),
                OriginDataFactory.Entity.makeOriginEntity(),
                LocationDataFactory.Entity.makeLocationEntity(),
                DataFactory.randomString(),
                DataFactory.randomIntList(5),
                DataFactory.randomString(),
                DataFactory.randomString(),
                isKilledByUser
            )
        }

        fun makeEntityCharactersModelList(count: Int): List<CharacterEntity> {
            val characters: MutableList<CharacterEntity> =
                ArrayList()
            for (i in 0 until count) {
                val character =
                    makeCharacterEntity()
                characters.add(character)
            }
            return characters
        }
    }
}
