package com.mohsenoid.rickandmorty.data.mapper

import androidx.annotation.VisibleForTesting
import com.mohsenoid.rickandmorty.data.db.entity.DbEntityCharacter
import com.mohsenoid.rickandmorty.data.db.entity.DbEntityLocation
import com.mohsenoid.rickandmorty.data.db.entity.DbEntityOrigin
import com.mohsenoid.rickandmorty.data.network.dto.NetworkCharacterModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkLocationModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkOriginModel

class CharacterDbMapper(
    private val originDbMapper: Mapper<NetworkOriginModel, DbEntityOrigin>,
    private val locationDbMapper: Mapper<NetworkLocationModel, DbEntityLocation>
) : Mapper<NetworkCharacterModel, DbEntityCharacter> {

    override fun map(input: NetworkCharacterModel): DbEntityCharacter {
        return DbEntityCharacter(
            id = input.id,
            name = input.name,
            status = input.status,
            statusAlive = input.status.equals(ALIVE, ignoreCase = true),
            species = input.species,
            type = input.type,
            gender = input.gender,
            origin = originDbMapper.map(input.origin),
            location = locationDbMapper.map(input.location),
            image = input.image,
            episodeIds = extractEpisodeIds(input.episodes),
            url = input.url,
            created = input.created,
            killedByUser = false
        )
    }

    companion object {
        @VisibleForTesting
        const val ALIVE: String = "alive"

        private const val SEPARATOR: Char = '/'

        fun extractEpisodeIds(episodes: List<String>): List<Int> {
            return episodes.map { it.split(SEPARATOR).last().toInt() }
        }
    }
}
