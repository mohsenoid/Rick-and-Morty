package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.entity.DbEntityEpisode
import com.mohsenoid.rickandmorty.domain.model.ModelEpisode

object EpisodeEntityMapper : Mapper<DbEntityEpisode, ModelEpisode> {

    override fun map(input: DbEntityEpisode): ModelEpisode {
        return ModelEpisode(
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
