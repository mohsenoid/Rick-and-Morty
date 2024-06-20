package com.mohsenoid.rickandmorty.test

import com.mohsenoid.rickandmorty.data.api.model.ApiCharacter
import com.mohsenoid.rickandmorty.data.db.model.DbCharacter
import com.mohsenoid.rickandmorty.domain.model.ModelCharacter

object CharacterDataFactory {

    internal fun makeDbCharacter(
        characterId: Int = DataFactory.randomInt(),
        status: String = DataFactory.randomString(),
        isAlive: Boolean = DataFactory.randomBoolean(),
        isKilledByUser: Boolean = false,
    ): DbCharacter =
        DbCharacter(
            id = characterId,
            name = DataFactory.randomString(),
            status = status,
            isAlive = isAlive,
            species = DataFactory.randomString(),
            type = DataFactory.randomString(),
            gender = DataFactory.randomString(),
            origin = OriginDataFactory.makeDbOrigin(),
            location = LocationDataFactory.makeDbLocation(),
            image = DataFactory.randomString(),
            episodeIds = DataFactory.randomIntList(count = 5),
            url = DataFactory.randomString(),
            created = DataFactory.randomString(),
            isKilledByUser = isKilledByUser,
        )

    internal fun makeDbCharacters(count: Int): List<DbCharacter> {
        val characters: MutableList<DbCharacter> = ArrayList()
        repeat(count) {
            val character: DbCharacter = makeDbCharacter()
            characters.add(character)
        }
        return characters
    }

    internal fun makeApiCharacter(characterId: Int = DataFactory.randomInt()): ApiCharacter =
        ApiCharacter(
            id = characterId,
            name = DataFactory.randomString(),
            status = DataFactory.randomString(),
            species = DataFactory.randomString(),
            type = DataFactory.randomString(),
            gender = DataFactory.randomString(),
            origin = OriginDataFactory.makeApiOrigin(),
            location = LocationDataFactory.makeApiLocation(),
            image = DataFactory.randomString(),
            episodes = DataFactory.randomIntList(count = 5)
                .map { "${DataFactory.randomString()}/$it" },
            url = DataFactory.randomString(),
            created = DataFactory.randomString(),
        )

    internal fun makeApiCharacters(count: Int): List<ApiCharacter> {
        val characters: MutableList<ApiCharacter> = ArrayList()
        repeat(count) {
            val character: ApiCharacter = makeApiCharacter()
            characters.add(character)
        }
        return characters
    }

    internal fun makeCharacter(
        id: Int = DataFactory.randomInt(),
        status: String = DataFactory.randomString(),
        isAlive: Boolean = DataFactory.randomBoolean(),
        isKilledByUser: Boolean = false,
    ): ModelCharacter = ModelCharacter(
        id = id,
        name = DataFactory.randomString(),
        status = status,
        isAlive = isAlive,
        species = DataFactory.randomString(),
        type = DataFactory.randomString(),
        gender = DataFactory.randomString(),
        origin = OriginDataFactory.makeOrigin(),
        location = LocationDataFactory.makeLocation(),
        imageUrl = DataFactory.randomString(),
        episodeIds = DataFactory.randomIntList(count = 5),
        url = DataFactory.randomString(),
        created = DataFactory.randomString(),
        isKilledByUser = isKilledByUser,
    )

    internal fun makeCharacters(count: Int): List<ModelCharacter> {
        val characters: MutableList<ModelCharacter> = ArrayList()
        repeat(count) {
            val character: ModelCharacter = makeCharacter()
            characters.add(character)
        }
        return characters
    }
}
