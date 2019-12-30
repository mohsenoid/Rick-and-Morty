package com.mohsenoid.rickandmorty.data.mapper

import com.mohsenoid.rickandmorty.data.db.dto.DbCharacterModel
import com.mohsenoid.rickandmorty.data.db.dto.DbLocationModel
import com.mohsenoid.rickandmorty.data.db.dto.DbOriginModel
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity
import com.mohsenoid.rickandmorty.domain.entity.LocationEntity
import com.mohsenoid.rickandmorty.domain.entity.OriginEntity
import javax.inject.Inject

class CharacterEntityMapper @Inject internal constructor(
    private val originEntityMapper: Mapper<DbOriginModel, OriginEntity>,
    private val locationEntityMapper: Mapper<DbLocationModel, LocationEntity>
) : Mapper<DbCharacterModel, CharacterEntity> {

    override fun map(input: DbCharacterModel): CharacterEntity {
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
