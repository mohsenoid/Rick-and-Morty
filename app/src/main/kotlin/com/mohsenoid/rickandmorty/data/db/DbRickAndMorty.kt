package com.mohsenoid.rickandmorty.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mohsenoid.rickandmorty.data.db.converters.DbTypeConverters
import com.mohsenoid.rickandmorty.data.db.dao.DbCharacterDaoAbs
import com.mohsenoid.rickandmorty.data.db.dao.DbEpisodeDao
import com.mohsenoid.rickandmorty.data.db.model.DbCharacter
import com.mohsenoid.rickandmorty.data.db.model.DbEpisode
import com.mohsenoid.rickandmorty.data.db.model.DbLocation
import com.mohsenoid.rickandmorty.data.db.model.DbOrigin

internal class DbRickAndMorty(context: Context) {

    private val db = Db.create(context)

    suspend fun insertEpisode(entityEpisode: DbEpisode) =
        db.episodeDao.insertEpisode(entityEpisode)

    suspend fun queryAllEpisodes(): List<DbEpisode> =
        db.episodeDao.queryAllEpisodes()

    suspend fun queryAllEpisodesByPage(page: Int, pageSize: Int): List<DbEpisode> =
        db.episodeDao.queryAllEpisodesByPage(page, pageSize)

    suspend fun insertCharacter(character: DbCharacter) =
        db.characterDao.insertCharacter(character)

    suspend fun queryCharactersByIds(characterIds: List<Int>): List<DbCharacter> =
        db.characterDao.queryCharactersByIds(characterIds)

    suspend fun queryCharacter(characterId: Int): DbCharacter? =
        db.characterDao.queryCharacter(characterId)

    suspend fun killCharacter(characterId: Int) =
        db.characterDao.killCharacter(characterId)

    suspend fun insertOrUpdateCharacter(character: DbCharacter) =
        db.characterDao.insertOrUpdateCharacter(character)

    @Database(
        entities = [
            DbCharacter::class,
            DbEpisode::class,
            DbLocation::class,
            DbOrigin::class,
        ],
        version = DATABASE_VERSION,
    )
    @TypeConverters(DbTypeConverters::class)
    internal abstract class Db : RoomDatabase() {

        abstract val episodeDao: DbEpisodeDao

        abstract val characterDao: DbCharacterDaoAbs

        companion object {
            fun create(context: Context): Db {
                return Room.databaseBuilder(
                    context,
                    Db::class.java,
                    DATABASE_NAME,
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }
    }

    companion object {
        private const val DATABASE_NAME: String = "rickandmorty.db"
        private const val DATABASE_VERSION: Int = 5
    }
}
