package com.mohsenoid.rickandmorty.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mohsenoid.rickandmorty.data.characters.dao.CharacterDao
import com.mohsenoid.rickandmorty.data.characters.entity.CharacterEntity
import com.mohsenoid.rickandmorty.data.episodes.dao.EpisodeDao
import com.mohsenoid.rickandmorty.data.episodes.entity.EpisodeEntity

@Database(
    entities = [EpisodeEntity::class, CharacterEntity::class],
    version = 1,
    exportSchema = true,
)
internal abstract class Database : RoomDatabase() {
    abstract fun episodeDao(): EpisodeDao

    abstract fun characterDao(): CharacterDao
}
