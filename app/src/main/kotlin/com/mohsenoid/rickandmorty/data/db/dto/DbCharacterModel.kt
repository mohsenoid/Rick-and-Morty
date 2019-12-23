package com.mohsenoid.rickandmorty.data.db.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class DbCharacterModel(

    @PrimaryKey
    @ColumnInfo(name = "_id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "status")
    val status: String,

    @ColumnInfo(name = "is_status_alive")
    val statusAlive: Boolean,

    @ColumnInfo(name = "species")
    val species: String,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "gender")
    val gender: String,

    @ColumnInfo(name = "origin")
    val origin: DbOriginModel,

    @ColumnInfo(name = "location")
    val location: DbLocationModel,

    @ColumnInfo(name = "image")
    val image: String,

    @ColumnInfo(name = "episode")
    val serializedEpisodes: String,

    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "created")
    val created: String,

    @ColumnInfo(name = "killedByUser", defaultValue = "0")
    val killedByUser: Boolean
)
