package com.mohsenoid.rickandmorty.data.db

import android.content.Context
import androidx.room.Room

internal object DatabaseProvider {
    private const val DATABASE_NAME = "db"

    private lateinit var instance: Database

    fun getDatabase(context: Context): Database {
        if (!::instance.isInitialized) {
            synchronized(Database::class.java) {
                instance =
                    Room.databaseBuilder(
                        context = context.applicationContext,
                        klass = Database::class.java,
                        name = DATABASE_NAME,
                    )
                        .fallbackToDestructiveMigration()
                        .build()
            }
        }
        return instance
    }
}
