package com.mohsenoid.rickandmorty.data.db

import android.content.Context
import androidx.room.Room

internal object DatabaseProvider {

    private const val DATABASE_NAME = "db"
    private lateinit var INSTANCE: Database

    fun getDatabase(context: Context): Database {
        if (!::INSTANCE.isInitialized) {
            synchronized(Database::class.java) {
                INSTANCE = Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = Database::class.java,
                    name = DATABASE_NAME,
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }
        return INSTANCE
    }
}
