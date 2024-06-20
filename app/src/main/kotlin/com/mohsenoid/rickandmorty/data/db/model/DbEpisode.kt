package com.mohsenoid.rickandmorty.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "episodes")
internal data class DbEpisode(

    @PrimaryKey
    @ColumnInfo(name = "_id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "air_date")
    val airDate: String,

    @ColumnInfo(name = "episode")
    val episode: String,

    @ColumnInfo(name = "character_ids")
    val characterIds: List<Int>,

    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "created")
    val created: String,
)
