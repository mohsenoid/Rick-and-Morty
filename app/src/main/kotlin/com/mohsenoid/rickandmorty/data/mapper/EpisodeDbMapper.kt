package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.dto.DbEpisodeModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkEpisodeModel
import com.mohsenoid.rickandmorty.util.extension.serializeStringList

class EpisodeDbMapper : Mapper<NetworkEpisodeModel, DbEpisodeModel> {

    override fun map(input: NetworkEpisodeModel): DbEpisodeModel {
        return DbEpisodeModel(
            id = input.id,
            name = input.name,
            airDate = input.airDate,
            episode = input.episode,
            serializedCharacterIds = getCharacterIds(input.characters).serializeStringList(),
            url = input.url,
            created = input.created
        )
    }

    companion object {
        private const val SEPARATOR = "/"

        fun getCharacterIds(characters: List<String>): List<String> {
            return characters.map { it.split(SEPARATOR).last() }
        }
    }
}
