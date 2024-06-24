package com.mohsenoid.rickandmorty.data.characters.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
internal data class CharacterEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "is_alive")
    val isAlive: Boolean,
    @ColumnInfo(name = "is_killed")
    val isKilled: Boolean,
    @ColumnInfo(name = "species")
    val species: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "gender")
    val gender: String,
    @ColumnInfo(name = "origin")
    val origin: String,
    @ColumnInfo(name = "location")
    val location: String,
    @ColumnInfo(name = "image")
    val image: String,
)
