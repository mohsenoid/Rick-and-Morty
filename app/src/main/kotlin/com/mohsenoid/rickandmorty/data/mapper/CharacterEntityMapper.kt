package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.entity.DbEntityCharacter
import com.mohsenoid.rickandmorty.data.db.entity.DbEntityLocation
import com.mohsenoid.rickandmorty.data.db.entity.DbEntityOrigin
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity
import com.mohsenoid.rickandmorty.domain.entity.LocationEntity
import com.mohsenoid.rickandmorty.domain.entity.OriginEntity

class CharacterEntityMapper(
    private val originEntityMapper: Mapper<DbEntityOrigin, OriginEntity>,
    private val locationEntityMapper: Mapper<DbEntityLocation, LocationEntity>
) : Mapper<DbEntityCharacter, CharacterEntity> {

    override fun map(input: DbEntityCharacter): CharacterEntity {
        return CharacterEntity(
            id = input.id,
            name = input.name,
            status = input.status,
            statusAlive = input.statusAlive,
            species = input.species,
            type = input.type,
            gender = input.gender,
            origin = originEntityMapper.map(input.origin),
            location = locationEntityMapper.map(input.location),
            imageUrl = input.image,
            episodeIds = input.episodeIds,
            url = input.url,
            created = input.created,
            killedByUser = input.killedByUser
        )
    }
}
