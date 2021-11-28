package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.entity.DbEntityCharacter
import com.mohsenoid.rickandmorty.data.network.dto.NetworkCharacterModel

object CharacterDbMapper : Mapper<NetworkCharacterModel, DbEntityCharacter> {

    const val ALIVE: String = "alive"

    private const val SEPARATOR: Char = '/'

    fun extractEpisodeIds(episodes: List<String>): List<Int> {
        return episodes.map { it.split(SEPARATOR).last().toInt() }
    }

    override fun map(input: NetworkCharacterModel): DbEntityCharacter {
        return DbEntityCharacter(
            id = input.id,
            name = input.name,
            status = input.status,
            statusAlive = input.status.equals(ALIVE, ignoreCase = true),
            species = input.species,
            type = input.type,
            gender = input.gender,
            origin = OriginDbMapper.map(input.origin),
            location = LocationDbMapper.map(input.location),
            image = input.image,
            episodeIds = extractEpisodeIds(input.episodes),
            url = input.url,
            created = input.created,
            killedByUser = false
        )
    }
}
