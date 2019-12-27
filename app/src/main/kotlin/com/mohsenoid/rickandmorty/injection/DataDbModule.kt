package com.mohsenoid.rickandmorty.injection

import android.app.Application
import com.mohsenoid.rickandmorty.data.db.Db
import com.mohsenoid.rickandmorty.data.db.DbCharacterDao
import com.mohsenoid.rickandmorty.data.db.DbEpisodeDao
import com.mohsenoid.rickandmorty.injection.qualifier.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataDbModule {

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Application): Db {
        return Db.create(context = context)
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
