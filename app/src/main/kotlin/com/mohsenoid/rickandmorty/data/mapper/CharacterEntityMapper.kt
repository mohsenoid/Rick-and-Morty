package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.entity.DbEntityCharacter
import com.mohsenoid.rickandmorty.domain.model.ModelCharacter

object CharacterEntityMapper : Mapper<DbEntityCharacter, ModelCharacter> {

    override fun map(input: DbEntityCharacter): ModelCharacter {
        return ModelCharacter(
            id = input.id,
            name = input.name,
            status = input.status,
            statusAlive = input.statusAlive,
            species = input.species,
            type = input.type,
            gender = input.gender,
            origin = OriginEntityMapper.map(input.origin),
            location = LocationEntityMapper.map(input.location),
            imageUrl = input.image,
            episodeIds = input.episodeIds,
            url = input.url,
            created = input.created,
            killedByUser = input.killedByUser
        )
    }
}
