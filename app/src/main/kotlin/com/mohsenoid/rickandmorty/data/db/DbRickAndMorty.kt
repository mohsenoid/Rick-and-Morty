package com.mohsenoid.rickandmorty.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mohsenoid.rickandmorty.data.db.converters.DbTypeConverters
import com.mohsenoid.rickandmorty.data.db.dao.DbCharacterDaoAbs
import com.mohsenoid.rickandmorty.data.db.dao.DbEpisodeDao
import com.mohsenoid.rickandmorty.data.db.entity.DbEntityCharacter
import com.mohsenoid.rickandmorty.data.db.entity.DbEntityEpisode
import com.mohsenoid.rickandmorty.data.db.entity.DbEntityLocation
import com.mohsenoid.rickandmorty.data.db.entity.DbEntityOrigin

internal class DbRickAndMorty(context: Context) {

    private val db = Db.create(context)

    suspend fun insertEpisode(entityEpisode: DbEntityEpisode) =
        db.episodeDao.insertEpisode(entityEpisode)

    suspend fun queryAllEpisodes(): List<DbEntityEpisode> =
        db.episodeDao.queryAllEpisodes()

    suspend fun queryAllEpisodesByPage(page: Int, pageSize: Int): List<DbEntityEpisode> =
        db.episodeDao.queryAllEpisodesByPage(page, pageSize)

    suspend fun insertCharacter(character: DbEntityCharacter) =
        db.characterDao.insertCharacter(character)

    suspend fun queryCharactersByIds(characterIds: List<Int>): List<DbEntityCharacter> =
        db.characterDao.queryCharactersByIds(characterIds)

    suspend fun queryCharacter(characterId: Int): DbEntityCharacter? =
        db.characterDao.queryCharacter(characterId)

    suspend fun killCharacter(characterId: Int) =
        db.characterDao.killCharacter(characterId)

    suspend fun insertOrUpdateCharacter(character: DbEntityCharacter) =
        db.characterDao.insertOrUpdateCharacter(character)

    @Database(
        entities = [
            DbEntityCharacter::class,
            DbEntityEpisode::class,
            DbEntityLocation::class,
            DbEntityOrigin::class,
        ],
        version = DATABASE_VERSION
    )
    @TypeConverters(DbTypeConverters::class)
    internal abstract class Db : RoomDatabase() {

        abstract val episodeDao: DbEpisodeDao

        abstract val characterDao: DbCharacterDaoAbs

        companion object {
            fun create(context: Context): Db {
                return Room.databaseBuilder(
                    context, Db::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }
    }

    companion object {
        const val DATABASE_NAME: String = "rickandmorty.db"
        const val DATABASE_VERSION: Int = 5
    }
}
