package com.mohsenoid.rickandmorty.data.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mohsenoid.rickandmorty.data.db.dto.DbCharacterModel
import com.mohsenoid.rickandmorty.data.db.dto.DbEpisodeModel
import com.mohsenoid.rickandmorty.data.db.dto.DbLocationModel
import com.mohsenoid.rickandmorty.data.db.dto.DbOriginModel

@Database(
    entities = [DbCharacterModel::class, DbEpisodeModel::class, DbLocationModel::class, DbOriginModel::class],
    version = DbConstants.DATABASE_VERSION
)
@TypeConverters(DbConverters::class)
abstract class Db : RoomDatabase() {

    abstract val episodeDao: DbEpisodeDao

    abstract val characterDao: DbCharacterDaoAbs

    companion object {
        fun create(context: Application): Db {
            return Room.databaseBuilder(
                context, Db::class.java,
                DbConstants.DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
