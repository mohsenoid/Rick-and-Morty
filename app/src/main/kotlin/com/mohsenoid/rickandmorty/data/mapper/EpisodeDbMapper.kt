package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.dto.DbEpisodeModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkEpisodeModel
import com.mohsenoid.rickandmorty.util.extension.mapStringListToIntegerList

class EpisodeDbMapper : Mapper<NetworkEpisodeModel, DbEpisodeModel> {

    override fun map(input: NetworkEpisodeModel): DbEpisodeModel {
        return DbEpisodeModel(
            id = input.id,
            name = input.name,
            airDate = input.airDate,
            episode = input.episode,
            characterIds = getCharacterIds(input.characters).mapStringListToIntegerList(),
            url = input.url,
            created = input.created
        )
    }

    companion object {
        private const val SEPARATOR: Char = '/'

        fun getCharacterIds(characters: List<String>): List<String> {
            return characters.map { it.split(SEPARATOR).last() }
        }
    }
}
