package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.entity.DbEntityEpisode
import com.mohsenoid.rickandmorty.data.network.dto.NetworkEpisodeModel

object EpisodeDbMapper : Mapper<NetworkEpisodeModel, DbEntityEpisode> {

    private const val SEPARATOR: Char = '/'

    fun extractCharacterIds(characters: List<String>): List<Int> {
        return characters.map { it.split(SEPARATOR).last().toInt() }
    }

    override fun map(input: NetworkEpisodeModel): DbEntityEpisode {
        return DbEntityEpisode(
            id = input.id,
            name = input.name,
            airDate = input.airDate,
            episode = input.episode,
            characterIds = extractCharacterIds(input.characters),
            url = input.url,
            created = input.created
        )
    }
}
