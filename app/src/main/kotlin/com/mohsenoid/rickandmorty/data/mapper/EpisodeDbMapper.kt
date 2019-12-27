package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.dto.DbEpisodeModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkEpisodeModel

class EpisodeDbMapper : Mapper<NetworkEpisodeModel, DbEpisodeModel> {

    override fun map(input: NetworkEpisodeModel): DbEpisodeModel {
        return DbEpisodeModel(
            id = input.id,
            name = input.name,
            airDate = input.airDate,
            episode = input.episode,
            characterIds = extractCharacterIds(input.characters),
            url = input.url,
            created = input.created
        )
    }

    companion object {
        private const val SEPARATOR: Char = '/'

        fun extractCharacterIds(characters: List<String>): List<Int> {
            return characters.map { it.split(SEPARATOR).last().toInt() }
        }
    }
}
