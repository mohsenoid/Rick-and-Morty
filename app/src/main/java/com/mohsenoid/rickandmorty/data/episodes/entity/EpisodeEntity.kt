package com.mohsenoid.rickandmorty.data.episodes.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mohsenoid.rickandmorty.data.db.util.IntSetConverter

@Entity(tableName = "episodes")
@TypeConverters(IntSetConverter::class)
internal data class EpisodeEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "page")
    val page: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "air_date")
    val airDate: String,
    @ColumnInfo(name = "episode")
    val episode: String,
    @ColumnInfo(name = "characters")
    val characters: Set<Int>,
)
