package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.Serializer
import com.mohsenoid.rickandmorty.data.db.dto.DbEpisodeModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkEpisodeModel

class EpisodeDbMapper : Mapper<NetworkEpisodeModel, DbEpisodeModel> {

    override fun map(input: NetworkEpisodeModel): DbEpisodeModel {
        return DbEpisodeModel(
            id = input.id,
            name = input.name,
            airDate = input.airDate,
            episode = input.episode,
            serializedCharacterIds = Serializer.serializeStringList(getCharacterIds(input.characters)),
            url = input.url,
            created = input.created
        )
    }

    private fun getCharacterIds(characters: List<String>): List<String> {
        return characters.map { it.split(SEPARATOR).last() }
    }

    companion object {
        private const val SEPARATOR = "/"
    }
}
