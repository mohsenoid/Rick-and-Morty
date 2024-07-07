package com.mohsenoid.rickandmorty.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mohsenoid.rickandmorty.data.characters.db.dao.CharacterDao
import com.mohsenoid.rickandmorty.data.characters.db.entity.CharacterEntity
import com.mohsenoid.rickandmorty.data.episodes.db.dao.EpisodeDao
import com.mohsenoid.rickandmorty.data.episodes.db.entity.EpisodeEntity

@Database(
    entities = [EpisodeEntity::class, CharacterEntity::class],
    version = 1,
    exportSchema = true,
)
internal abstract class Database : RoomDatabase() {
    abstract fun episodeDao(): EpisodeDao

    abstract fun characterDao(): CharacterDao
}
