package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.dto.DbEpisodeModel
import com.mohsenoid.rickandmorty.domain.entity.EpisodeEntity

class EpisodeEntityMapper : Mapper<DbEpisodeModel, EpisodeEntity> {

    override fun map(input: DbEpisodeModel): EpisodeEntity {
        return EpisodeEntity(
            id = input.id,
            name = input.name,
            airDate = input.airDate,
            episode = input.episode,
            characterIds = input.characterIds,
            url = input.url,
            created = input.created
        )
    }
}
