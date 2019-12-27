package com.mohsenoid.rickandmorty.data.db

import android.app.Application
import androidx.room.Room
import com.mohsenoid.rickandmorty.injection.qualifier.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Application): Db {
        return Room.databaseBuilder(context, Db::class.java, DbConstants.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideEpisodeDao(db: Db): DbEpisodeDao {
        return db.episodeDao
    }

    @Provides
    @Singleton
    fun provideDbCharacterDao(db: Db): DbCharacterDao {
        return db.characterDao
    }
}
